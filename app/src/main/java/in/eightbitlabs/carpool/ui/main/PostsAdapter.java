package in.eightbitlabs.carpool.ui.main;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.eightbitlabs.carpool.R;
import in.eightbitlabs.carpool.data.model.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private SortedList<Post> mPosts;

    @Inject
    public PostsAdapter() {
        mPosts = new SortedList<>(Post.class, new SortedListAdapterCallback<Post>(this) {
            @Override
            public int compare(Post o1, Post o2) {
                return (int) (o2.timestamp() - o1.timestamp());
            }

            @Override
            public boolean areContentsTheSame(Post oldItem, Post newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Post item1, Post item2) {
                return item1.timestamp() == item2.timestamp();
            }
        });
    }

    public void setPosts(List<Post> posts) {
        mPosts.addAll(posts);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.typeTextView.setText(post.type());
        holder.modelTextView.setText(post.model());
        holder.licenseTextView.setText(post.license());
        holder.phoneTextView.setText(""+post.contact());
        holder.pickupTextView.setText(post.pickup());
//        holder.hexColorView.setBackgroundColor(Color.parseColor(post.profile().hexColor()));
        holder.nameTextView.setText(post.profile().name());
        holder.branchTextView.setText(post.profile().branch());
//        if(post.rate() != null) {
//            holder.rateTextView.setText(post.rate());
//        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_type) TextView typeTextView;
        @BindView(R.id.text_model) TextView modelTextView;
        @BindView(R.id.text_license) TextView licenseTextView;
        @BindView(R.id.text_rate) TextView rateTextView;
        @BindView(R.id.text_phone) TextView phoneTextView;
        @BindView(R.id.text_pickup) TextView pickupTextView;
        @BindView(R.id.view_hex_color) View hexColorView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_branch) TextView branchTextView;

        PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
