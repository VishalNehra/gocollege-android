package in.eightbitlabs.gocollege.ui.create;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.eightbitlabs.gocollege.R;
import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import in.eightbitlabs.gocollege.ui.base.BaseActivity;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author shalzz
 */

public class CreatePostActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_type)
    Spinner type;
    @BindView(R.id.edit_model)
    EditText model;
    @BindView(R.id.edit_license)
    EditText license;
    @BindView(R.id.spinner_seats)
    Spinner seats;
    @BindView(R.id.edit_pickup)
    EditText pickup;
    @BindView(R.id.edit_rate)
    EditText rate;
    @BindView(R.id.edit_contact)
    EditText contact;

    @Inject
    GoCollegeService mGoCollegeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.button_post)
    void post() {
        if(!isValid())
            return;

        Post.Builder post = Post.builder()
                .timestamp(new Date().getTime())
                .type(type.getSelectedItem().toString())
                .model(model.getText().toString())
                .license(license.getText().toString())
                .seats(Integer.parseInt(seats.getSelectedItem().toString()))
                .pickup(pickup.getText().toString())
                .contact(Long.parseLong(contact.getText().toString()));

        if(rate.getText().length() != 0 )
            post.rate(Integer.parseInt(rate.getText().toString()));

        mGoCollegeService.post(post.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e,"Error posting!");
                        Snackbar.make(mToolbar,"Error posting!",Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        Toast.makeText(CreatePostActivity.this,
                                "Post added successfully",Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                });
    }

    boolean isValid() {
        if(type.getSelectedItemPosition() == 0) {
            return false;
        }
        if(model.getText().length() == 0) {
            model.setError(getString(R.string.error_required_field));
            return false;
        }
        if(license.getText().length() == 0) {
            license.setError(getString(R.string.error_required_field));
            return false;
        }
        if(seats.getSelectedItemPosition() == 0) {
            return false;
        }
        if(pickup.getText().length() == 0) {
            pickup.setError(getString(R.string.error_required_field));
            return false;
        }
        if(rate.getText().length() == 0) {
            rate.setError(getString(R.string.error_required_field));
            return false;
        }
        if(contact.getText().length() == 0) {
            contact.setError(getString(R.string.error_required_field));
            return false;
        }
        return true;
    }
}
