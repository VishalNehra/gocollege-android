package in.eightbitlabs.carpool.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.eightbitlabs.carpool.R;
import in.eightbitlabs.carpool.ui.base.BaseActivity;
import in.eightbitlabs.carpool.ui.main.MainActivity;
import in.eightbitlabs.carpool.util.DialogFactory;
import timber.log.Timber;

/**
 * @author shalzz
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fb_login_button)
    LoginButton mLoginButton;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mLoginPresenter.attachView(this);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions("public_profile","email");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoginProgress();
                mLoginPresenter.login();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                Timber.e(exception,"Facebook login exception");
                showLoginError();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showError(String msg) {
        Snackbar.make(findViewById(android.R.id.content),msg,Snackbar.LENGTH_LONG);
    }

    @Override
    public void showLoginError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_logging_in))
                .show();
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void showLoginProgress() {
        DialogFactory.createProgressDialog(this, R.string.logging_in).show();
    }
}
