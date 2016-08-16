package in.eightbitlabs.carpool.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.eightbitlabs.carpool.R;
import in.eightbitlabs.carpool.data.model.Post;
import in.eightbitlabs.carpool.ui.base.BaseActivity;
import in.eightbitlabs.carpool.ui.login.LoginActivity;
import in.eightbitlabs.carpool.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;
    @Inject PostsAdapter mPostsAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mRecyclerView.setAdapter(mPostsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                showLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    @OnClick(R.id.fab)
    void createPost() {

    }

    /***** MVP View methods implementation *****/

    @Override
    public void showPosts(List<Post> posts) {
        mPostsAdapter.setPosts(posts);
        mPostsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(mToolbar,msg,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showPostsEmpty() {
        mPostsAdapter.setPosts(Collections.emptyList());
        mPostsAdapter.notifyDataSetChanged();
        Snackbar.make(mToolbar,
                R.string.empty_posts,Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, v -> mMainPresenter.loadPosts())
                .show();
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
