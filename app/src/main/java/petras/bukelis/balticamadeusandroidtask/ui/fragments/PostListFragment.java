package petras.bukelis.balticamadeusandroidtask.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import petras.bukelis.balticamadeusandroidtask.R;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.network.services.RetroFitResponseListener;
import petras.bukelis.balticamadeusandroidtask.ui.adapter.PostAdapter;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostViewModel;

public class PostListFragment extends Fragment {
    private PostViewModel postViewModel;
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private List<Post> tempPostList;
    private SwipeRefreshLayout refreshView;
    private boolean cicleFinished = false;
    public PostListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view_posts);
        refreshView = view.findViewById(R.id.refreshView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        loadDataFromApi();

    }
    private static class PostListener implements PostAdapter.PostAdapterListener {

        @Override
        public void onPostSelected(Post post, View view) {
            Navigation.findNavController(view).navigate(
                    PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post));
        }
    }
    private void clearRecyclerView()
    {
        List<Post> tempPostemptylist = new ArrayList<Post>();
        tempPostemptylist.clear();
        mPostAdapter = new PostAdapter(tempPostemptylist, new PostListener());
        mRecyclerView.setAdapter(mPostAdapter);
    }

    private void imitateDelayRefresh()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDataFromApi();
                Toast.makeText(getContext(), "Information refreshed!", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("AlertDialog");
        builder.setMessage("Network connection failed, please check your internet connectivity and try again...");
        // add the buttons with listeners
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Retrying data loading...", Toast.LENGTH_SHORT).show();
                loadDataFromApi();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Data loading was canceled...", Toast.LENGTH_SHORT).show();
                // Did not know what exactly to do here, as in task there was no action specified after canceling.
                // I would think there still should be a way for user to try again, so cancel button should not be an option
                // that way the it would reduce elements on screen and show user clear path of action.
            }
        });
        // create and show the alert dialog

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadDataFromApi()
    {
        cicleFinished = false;
        postViewModel.loadPosts(new RetroFitResponseListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Data Loaded successfully", Toast.LENGTH_SHORT).show();

                refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        clearRecyclerView();
                        // I imitate delay of refresh, just to show that its working.
                        imitateDelayRefresh();
                    }
                });

                postViewModel.getAllPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
                    @Override
                    public void onChanged(List<Post> posts) {
                        refreshView.setRefreshing(true);
                        tempPostList = posts;
                        mPostAdapter = new PostAdapter(posts, new PostListener());
                        mRecyclerView.setAdapter(mPostAdapter);
                        refreshView.setRefreshing(false);
                    }
                });

                postViewModel.getPostListObserver().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
                    @Override
                    public void onChanged(List<Post> posts) {
                        refreshView.setRefreshing(true);
                        try {
                            if(!cicleFinished) {
                                for (Post post : posts) {
                                    if (post != null && tempPostList != null) {
                                        if (!tempPostList.contains(post)) {
                                            postViewModel.insert(new Post(post.getUserId(), post.getTitle(), post.getBody()));
                                        }
                                    }

                                }
                                cicleFinished = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        refreshView.setRefreshing(false);
                    }
                });


            }

            @Override
            public void onFailure() {
                showAlertDialog(getView());
                Toast.makeText(getContext(), "Data Loading failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}