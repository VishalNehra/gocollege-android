package in.eightbitlabs.gocollege.ui.main;

import java.util.List;

import javax.inject.Inject;

import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import in.eightbitlabs.gocollege.injection.ConfigPersistent;
import in.eightbitlabs.gocollege.ui.base.BasePresenter;
import in.eightbitlabs.gocollege.util.RxUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final GoCollegeService mGoCollegeService;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(GoCollegeService goCollegeService) {
        mGoCollegeService = goCollegeService;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadPosts(int page) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mGoCollegeService.getPosts(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the posts.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        if (posts.isEmpty() && page == 0) {
                            getMvpView().showPostsEmpty();
                        } else {
                            getMvpView().showPosts(posts);
                        }
                    }
                });
    }

}
