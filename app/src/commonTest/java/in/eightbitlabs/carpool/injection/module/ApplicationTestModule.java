package in.eightbitlabs.carpool.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.eightbitlabs.carpool.data.DataManager;
import in.eightbitlabs.carpool.data.remote.CarpoolService;
import in.eightbitlabs.carpool.injection.ApplicationContext;

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
    CarpoolService provideRibotsService() {
        return mock(CarpoolService.class);
    }

}
