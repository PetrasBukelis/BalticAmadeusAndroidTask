package petras.bukelis.balticamadeusandroidtask.ui.fragments;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import petras.bukelis.balticamadeusandroidtask.R;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.entities.User;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostDetailViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.UserViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.factory.DetailFragmentViewModelFactory;


public class PostDetailFragment extends Fragment {

    private Post tempPost;
    private List<User> tempUserList;
    ImageView userPhotoImageView;
    public PostDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        userPhotoImageView = view.findViewById(R.id.user_photo_image_view);
        TextView userNameTextView = view.findViewById(R.id.user_name_text_view);
        TextView postTitleTextView = view.findViewById(R.id.post_title_text_view);
        TextView postBodyTextView = view.findViewById(R.id.post_body_text_view);

        Application application = requireActivity().getApplication();
        Post post = PostDetailFragmentArgs.fromBundle(requireArguments()).getDetailFragmentArgs();
        DetailFragmentViewModelFactory factory = new DetailFragmentViewModelFactory(application,post);
        PostDetailViewModel viewModel = new ViewModelProvider(this,factory).get(PostDetailViewModel.class);
        UserViewModel  userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getSelectedPost().observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                tempPost = post;
                postTitleTextView.setText(post.getTitle());
                postBodyTextView.setText(post.getBody());
            }
        });
        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                tempUserList = users;
                for (User u: users) {
                    if(u.getId() == tempPost.getUserId())
                    {
                        userNameTextView.setText(u.getName());
                        SetUpImage(u.getId());
                    }
                }
            }
        });
        userViewModel.getUserListObserver().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (User user: users) {
                    if(user != null) {
                        if (!tempUserList.contains(user)) {
                            userViewModel.insert(new User(user.getName(),user.getUsername()));
                            Toast.makeText(application, user.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        userViewModel.loadUsers();

        return view;
    }
    private void SetUpImage(int userid) {

        try {
            String imageUrl = "https://source.unsplash.com/collection/542909/?sig=" + userid;
            Picasso.with(getContext()).load(imageUrl).into(userPhotoImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}