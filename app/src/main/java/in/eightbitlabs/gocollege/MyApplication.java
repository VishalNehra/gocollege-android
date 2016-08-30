package in.eightbitlabs.gocollege;

import android.app.Application;
import android.content.Context;

import com.bugsnag.android.BeforeNotify;
import com.bugsnag.android.Bugsnag;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import in.eightbitlabs.gocollege.injection.component.ApplicationComponent;
import in.eightbitlabs.gocollege.injection.component.DaggerApplicationComponent;
import in.eightbitlabs.gocollege.injection.module.ApplicationModule;
import in.eightbitlabs.gocollege.util.BugsnagTree;
import timber.log.Timber;

public class MyApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Bugsnag.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            final BugsnagTree tree = new BugsnagTree();
            Bugsnag.getClient().beforeNotify(error -> {
                tree.update(error);
                return true;
            });

            Timber.plant(tree);
        }
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
