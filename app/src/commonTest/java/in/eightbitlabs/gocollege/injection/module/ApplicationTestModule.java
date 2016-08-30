package in.eightbitlabs.gocollege.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.eightbitlabs.gocollege.data.DataManager;
import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import in.eightbitlabs.gocollege.injection.ApplicationContext;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    GoCollegeService provideRibotsService() {
        return mock(GoCollegeService.class);
    }

}
