package in.eightbitlabs.gocollege.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;

import in.eightbitlabs.gocollege.ui.base.BaseActivity;
import in.eightbitlabs.gocollege.ui.login.LoginActivity;
import in.eightbitlabs.gocollege.ui.main.MainActivity;

/**
 * @author shalzz
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AccessToken.getCurrentAccessToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
