package petras.bukelis.balticamadeusandroidtask.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import petras.bukelis.balticamadeusandroidtask.R;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostViewModel;
import petras.bukelis.balticamadeusandroidtask.viewmodels.UserViewModel;

public class PostActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    private UserViewModel userViewModel;

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController);

    }
    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp();
    }
}