package in.eightbitlabs.carpool.ui.login;

import in.eightbitlabs.carpool.ui.base.MvpView;

/**
 * @author shalzz
 */
public interface LoginMvpView extends MvpView {

    public void showError(String msg);

    public void showLoginError();

    public void showMainActivity();

    public void showLoginProgress();

    public void dismissLoginProgress();
}
