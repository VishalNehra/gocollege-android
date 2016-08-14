package in.eightbitlabs.carpool.ui.main;

import java.util.List;

import in.eightbitlabs.carpool.data.model.Post;
import in.eightbitlabs.carpool.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPosts(List<Post> posts);

    void showPostsEmpty();

    void showError();

}
