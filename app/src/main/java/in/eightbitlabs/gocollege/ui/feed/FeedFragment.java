package in.eightbitlabs.gocollege.ui.feed;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.eightbitlabs.gocollege.R;
import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.injection.ActivityContext;
import in.eightbitlabs.gocollege.ui.main.MainActivity;
import in.eightbitlabs.gocollege.util.DialogFactory;
import in.eightbitlabs.gocollege.util.EndlessRecyclerViewScrollListener;

/**
 * @author shalzz
 */
public class FeedFragment extends Fragment implements FeedMvpView{

    public  static final int REQUEST_CREATE_ACTIVITY = 1;

    @Inject FeedPresenter mFeedPresenter;
    @Inject PostsAdapter mPostsAdapter;
    @ActivityContext @Inject Context mContext;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_feed,container);
        unbinder = ButterKnife.bind(this,view);

        ((MainActivity) getActivity()).activityComponent().inject(this);
        mFeedPresenter.attachView(this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mRecyclerView.setAdapter(mPostsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                FeedFragment.this.page = page;
                mFeedPresenter.loadPosts(page);
            }
        });
        mFeedPresenter.loadPosts(page);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_feed, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mFeedPresenter.loadPosts(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mFeedPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CREATE_ACTIVITY && resultCode == Activity.RESULT_OK) {
            mFeedPresenter.loadPosts(0);
        }
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showPosts(List<Post> posts) {
        mPostsAdapter.addPosts(posts);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(mRecyclerView,msg,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(mContext, getString(R.string.error_loading_posts))
                .show();
    }

    @Override
    public void showPostsEmpty() {
        mPostsAdapter.addPosts(Collections.emptyList());
        mPostsAdapter.notifyDataSetChanged();
        Snackbar.make(mRecyclerView,
                R.string.empty_posts,Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action_retry, v -> mFeedPresenter.loadPosts(page))
                .show();
    }
}
