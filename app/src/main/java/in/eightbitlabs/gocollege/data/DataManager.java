package in.eightbitlabs.gocollege.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.eightbitlabs.gocollege.data.local.DatabaseHelper;
import in.eightbitlabs.gocollege.data.local.PreferencesHelper;
import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import rx.Observable;

@Singleton
public class DataManager {

    private final GoCollegeService mGoCollegeService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(GoCollegeService goCollegeService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mGoCollegeService = goCollegeService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<List<Post>> getPosts() {
        return mGoCollegeService.getPosts(1);
    }
}
