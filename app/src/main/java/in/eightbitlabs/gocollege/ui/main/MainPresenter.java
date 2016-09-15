package in.eightbitlabs.gocollege.ui.main;

import javax.inject.Inject;

import in.eightbitlabs.gocollege.injection.ConfigPersistent;
import in.eightbitlabs.gocollege.ui.base.BasePresenter;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
