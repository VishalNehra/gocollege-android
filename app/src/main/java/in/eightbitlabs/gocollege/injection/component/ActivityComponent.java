package in.eightbitlabs.gocollege.injection.component;

import dagger.Subcomponent;
import in.eightbitlabs.gocollege.injection.PerActivity;
import in.eightbitlabs.gocollege.injection.module.ActivityModule;
import in.eightbitlabs.gocollege.ui.create.CreatePostActivity;
import in.eightbitlabs.gocollege.ui.feed.FeedFragment;
import in.eightbitlabs.gocollege.ui.login.LoginActivity;
import in.eightbitlabs.gocollege.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(CreatePostActivity createPostActivity);

    void inject(FeedFragment feedFragment);
}
