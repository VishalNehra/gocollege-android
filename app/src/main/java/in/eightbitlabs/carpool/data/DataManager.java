package in.eightbitlabs.carpool.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.eightbitlabs.carpool.data.local.DatabaseHelper;
import in.eightbitlabs.carpool.data.local.PreferencesHelper;
import in.eightbitlabs.carpool.data.model.Post;
import in.eightbitlabs.carpool.data.remote.CarpoolService;
import rx.Observable;

@Singleton
public class DataManager {

    private final CarpoolService mCarpoolService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(CarpoolService carpoolService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mCarpoolService = carpoolService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<List<Post>> getPosts() {
        return mCarpoolService.getPosts(1);
    }
}
