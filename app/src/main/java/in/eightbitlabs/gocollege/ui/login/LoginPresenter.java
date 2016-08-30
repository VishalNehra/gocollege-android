package in.eightbitlabs.gocollege.ui.login;

import com.facebook.login.LoginManager;

import javax.inject.Inject;

import in.eightbitlabs.gocollege.data.remote.GoCollegeService;
import in.eightbitlabs.gocollege.injection.ConfigPersistent;
import in.eightbitlabs.gocollege.ui.base.BasePresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author shalzz
 */
@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    GoCollegeService mGoCollegeService;

    @Inject
    public LoginPresenter(GoCollegeService goCollegeService) {
        mGoCollegeService = goCollegeService;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void login() {
        mGoCollegeService.verifyUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().dismissLoginProgress();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error,"Login error");
                        getMvpView().showLoginError();
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getMvpView().showMainActivity();
                    }
                });
    }
}
