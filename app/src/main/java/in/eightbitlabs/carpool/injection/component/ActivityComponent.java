package in.eightbitlabs.carpool.injection.component;

import dagger.Subcomponent;
import in.eightbitlabs.carpool.injection.PerActivity;
import in.eightbitlabs.carpool.injection.module.ActivityModule;
import in.eightbitlabs.carpool.ui.login.LoginActivity;
import in.eightbitlabs.carpool.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);
}
