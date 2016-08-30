package in.eightbitlabs.gocollege.ui.main;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.eightbitlabs.gocollege.R;
import in.eightbitlabs.gocollege.data.model.Post;
import in.eightbitlabs.gocollege.injection.ApplicationContext;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private SortedList<Post> mPosts;
    private Context mContext;

    @Inject
    PostsAdapter(@ApplicationContext Context context) {
        mContext = context;
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

    void addPosts(List<Post> posts) {
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
        holder.seatsTextView.setText(mContext.getResources()
                .getQuantityString(R.plurals.post_item_seats, post.seats(), post.seats()));
        holder.phoneTextView.setText(mContext.getString(R.string.post_item_contact, post.contact()));
        holder.pickupTextView.setText(mContext.getString(R.string.post_item_pickup, post.pickup()));
        holder.nameTextView.setText(post.profile().name());
        holder.branchTextView.setText(post.profile().branch());
        if(post.rate() != null) {
            holder.rateTextView.setText(mContext.getResources()
                    .getQuantityString(R.plurals.post_item_rate, post.rate(), post.rate()));
        } else {
            holder.rateTextView.setVisibility(View.GONE);
        }

        Glide.with(mContext)
                .load(post.profile().picture())
                .into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_type) TextView typeTextView;
        @BindView(R.id.text_model) TextView modelTextView;
        @BindView(R.id.text_seats) TextView seatsTextView;
        @BindView(R.id.text_license) TextView licenseTextView;
        @BindView(R.id.text_rate) TextView rateTextView;
        @BindView(R.id.text_phone) TextView phoneTextView;
        @BindView(R.id.text_pickup) TextView pickupTextView;
        @BindView(R.id.view_profile_photo) ImageView photoView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_branch) TextView branchTextView;

        PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
