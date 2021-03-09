package petras.bukelis.balticamadeusandroidtask.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.R;
import petras.bukelis.balticamadeusandroidtask.entities.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private List<Post> allPosts;
    private PostAdapterListener mListener;

    public PostAdapter(List<Post> postList, PostAdapterListener listener) {
        this.allPosts = postList;
        this.mListener = listener;
    }

    public interface PostAdapterListener {
        void onPostSelected(Post post, View view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView mPostNameTextView;

        PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            mPostNameTextView = itemView.findViewById(R.id.post_title_text_item_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onPostSelected(allPosts.get(getAdapterPosition()), itemView);
                }
            });
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Log.d("DATAFLOWBINDVIEWHOLDER", Integer.toString(allPosts.size()));

        Post post = allPosts.get(position);
        holder.mPostNameTextView.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return allPosts.size();
    }
}
