package in.eightbitlabs.carpool.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import in.eightbitlabs.carpool.data.DataManager;
import in.eightbitlabs.carpool.data.local.DatabaseHelper;
import in.eightbitlabs.carpool.data.local.PreferencesHelper;
import in.eightbitlabs.carpool.data.remote.CarpoolService;
import in.eightbitlabs.carpool.injection.ApplicationContext;
import in.eightbitlabs.carpool.injection.module.ApplicationModule;
import in.eightbitlabs.carpool.injection.module.NetworkModule;
import in.eightbitlabs.carpool.util.RxEventBus;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    CarpoolService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
