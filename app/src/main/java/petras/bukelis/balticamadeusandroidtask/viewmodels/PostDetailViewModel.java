package petras.bukelis.balticamadeusandroidtask.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import petras.bukelis.balticamadeusandroidtask.entities.Post;

public class PostDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Post> selectedPost = new MutableLiveData<>();

    public PostDetailViewModel(@NonNull Application application, Post post) {
        super(application);
        selectedPost.postValue(post);
    }

    public MutableLiveData<Post> getSelectedPost()
    {
        return selectedPost;
    }
}
