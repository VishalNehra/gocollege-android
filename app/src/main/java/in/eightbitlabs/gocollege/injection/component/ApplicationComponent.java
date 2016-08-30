package in.eightbitlabs.gocollege.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import in.eightbitlabs.gocollege.data.DataManager;
import in.eightbitlabs.gocollege.data.local.DatabaseHelper;
import in.eightbitlabs.gocollege.data.local.PreferencesHelper;
import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import in.eightbitlabs.gocollege.injection.ApplicationContext;
import in.eightbitlabs.gocollege.injection.module.ApplicationModule;
import in.eightbitlabs.gocollege.injection.module.NetworkModule;
import in.eightbitlabs.gocollege.util.RxEventBus;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    GoCollegeService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
