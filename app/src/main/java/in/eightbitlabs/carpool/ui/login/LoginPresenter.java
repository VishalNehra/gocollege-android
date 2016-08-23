package in.eightbitlabs.carpool.ui.login;

import com.facebook.login.LoginManager;

import javax.inject.Inject;

import in.eightbitlabs.carpool.data.remote.CarpoolService;
import in.eightbitlabs.carpool.injection.ConfigPersistent;
import in.eightbitlabs.carpool.ui.base.BasePresenter;
import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author shalzz
 */
@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    CarpoolService mCarpoolService;

    @Inject
    public LoginPresenter(CarpoolService carpoolService) {
        mCarpoolService = carpoolService;
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
        mCarpoolService.verifyUser()
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
