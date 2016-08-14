package in.eightbitlabs.carpool.ui.main;

import java.util.List;

import javax.inject.Inject;

import in.eightbitlabs.carpool.data.model.Post;
import in.eightbitlabs.carpool.data.remote.CarpoolService;
import in.eightbitlabs.carpool.injection.ConfigPersistent;
import in.eightbitlabs.carpool.ui.base.BasePresenter;
import in.eightbitlabs.carpool.util.RxUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final CarpoolService mCarpoolService;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(CarpoolService carpoolService) {
        mCarpoolService = carpoolService;
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

    public void loadPosts() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mCarpoolService.getPosts(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Post> ribots) {
                        if (ribots.isEmpty()) {
                            getMvpView().showPostsEmpty();
                        } else {
                            getMvpView().showPosts(ribots);
                        }
                    }
                });
    }

}
