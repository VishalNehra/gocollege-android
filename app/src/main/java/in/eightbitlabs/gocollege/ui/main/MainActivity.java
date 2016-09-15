package in.eightbitlabs.gocollege.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.eightbitlabs.gocollege.R;
import in.eightbitlabs.gocollege.ui.base.BaseActivity;
import in.eightbitlabs.gocollege.ui.create.CreatePostActivity;
import in.eightbitlabs.gocollege.ui.login.LoginActivity;

import static in.eightbitlabs.gocollege.ui.feed.FeedFragment.REQUEST_CREATE_ACTIVITY;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mMainPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                showLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    @OnClick(R.id.fab)
    public void showCreatePost() {
        startActivityForResult(new Intent(this, CreatePostActivity.class), REQUEST_CREATE_ACTIVITY);
    }
}
