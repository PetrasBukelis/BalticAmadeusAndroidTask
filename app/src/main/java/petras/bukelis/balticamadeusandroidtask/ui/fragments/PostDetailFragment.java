package petras.bukelis.balticamadeusandroidtask.ui.fragments;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.R;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.entities.User;
import petras.bukelis.balticamadeusandroidtask.network.services.RetroFitResponseListener;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostDetailViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.UserViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.factory.DetailFragmentViewModelFactory;


public class PostDetailFragment extends Fragment {
    TextView userNameTextView;
    TextView postTitleTextView;
    TextView postBodyTextView;
    PostDetailViewModel viewModel;
    UserViewModel  userViewModel;
    Application application;
    private Post tempPost;
    private List<User> tempUserList;
    ImageView userPhotoImageView;
    private boolean cicleFinished;
    public PostDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        userPhotoImageView = view.findViewById(R.id.user_photo_image_view);
         userNameTextView = view.findViewById(R.id.user_name_text_view);
         postTitleTextView = view.findViewById(R.id.post_title_text_view);
        postBodyTextView = view.findViewById(R.id.post_body_text_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = requireActivity().getApplication();
        Post post = PostDetailFragmentArgs.fromBundle(requireArguments()).getDetailFragmentArgs();
        DetailFragmentViewModelFactory factory = new DetailFragmentViewModelFactory(application,post);
        viewModel = new ViewModelProvider(this,factory).get(PostDetailViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        loadDataFromApi();
    }

    private void SetUpImage(int userid) {

        try {
            String imageUrl = "https://source.unsplash.com/collection/542909/?sig=" + userid;
            Picasso.with(getContext()).load(imageUrl).into(userPhotoImageView);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(application, "Image loading failed...", Toast.LENGTH_SHORT).show();
        }


    }
    private void loadDataFromApi()
    {
        cicleFinished = false;
        userViewModel.loadUsers(new RetroFitResponseListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Data Loaded successfully", Toast.LENGTH_SHORT).show();

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
                        boolean userFound = false;
                        tempUserList = users;
                        for (User u: users) {
                            if(u.getId() == tempPost.getUserId())
                            {
                                userNameTextView.setText(u.getName());
                                SetUpImage(u.getId());
                                userFound = true;
                            }
                        }
                        if(!userFound)
                        {
                            userNameTextView.setText(R.string.usernotfoundstring);
                            postTitleTextView.setText(R.string.usernotfoundstring);
                            postBodyTextView.setText(R.string.usernotfoundstring);
                        }

                    }
                });
                userViewModel.getUserListObserver().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {

                        try {
                            if(!cicleFinished) {
                                for (User user : users) {
                                    if (user != null && tempUserList != null) {
                                        if (!tempUserList.contains(user)) {
                                            userViewModel.insert(new User(user.getName(), user.getUsername()));
                                        }
                                    }
                                }
                                cicleFinished = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                // I would think there still should be a way for user to try again, so cancel button should be an option
                // that way the it would reduce elements on screen and show user clear path of action.
            }
        });
        // create and show the alert dialog

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}