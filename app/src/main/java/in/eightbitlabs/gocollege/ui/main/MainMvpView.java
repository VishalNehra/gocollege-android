package in.eightbitlabs.gocollege.ui.main;

import java.util.List;

import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPosts(List<Post> posts);

    void showPostsEmpty();

    void showError();

    void showError(String msg);

    void showLogin();

}
