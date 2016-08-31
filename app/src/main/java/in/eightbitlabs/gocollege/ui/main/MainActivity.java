package in.eightbitlabs.gocollege.ui.main;

import android.app.Activity;
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
import in.eightbitlabs.gocollege.R;
import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.ui.base.BaseActivity;
import in.eightbitlabs.gocollege.ui.create.CreatePostActivity;
import in.eightbitlabs.gocollege.ui.login.LoginActivity;
import in.eightbitlabs.gocollege.util.DialogFactory;
import in.eightbitlabs.gocollege.util.EndlessRecyclerViewScrollListener;

import static junit.runner.Version.id;

public class MainActivity extends BaseActivity implements MainMvpView {

    public  static final int REQUEST_CREATE_ACTIVITY = 1;

    @Inject MainPresenter mMainPresenter;
    @Inject PostsAdapter mPostsAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mMainPresenter.attachView(this);

        mRecyclerView.setAdapter(mPostsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                MainActivity.this.page = page;
                mMainPresenter.loadPosts(page);
            }
        });
        mMainPresenter.loadPosts(page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mMainPresenter.loadPosts(0);
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CREATE_ACTIVITY && resultCode == Activity.RESULT_OK) {
            mMainPresenter.loadPosts(0);
        }
    }

    @OnClick(R.id.fab)
    void showCreatePost() {
        startActivityForResult(new Intent(this, CreatePostActivity.class), REQUEST_CREATE_ACTIVITY);
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showPosts(List<Post> posts) {
        mPostsAdapter.addPosts(posts);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(mToolbar,msg,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_posts))
                .show();
    }

    @Override
    public void showPostsEmpty() {
        mPostsAdapter.addPosts(Collections.emptyList());
        mPostsAdapter.notifyDataSetChanged();
        Snackbar.make(mToolbar,
                R.string.empty_posts,Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action_retry, v -> mMainPresenter.loadPosts(page))
                .show();
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
