package petras.bukelis.balticamadeusandroidtask.viewmodels.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.viewmodels.PostDetailViewModel;

public class DetailFragmentViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private Post mPost;
    public DetailFragmentViewModelFactory(Application application, Post post)
    {
        this.mApplication = application;
        this.mPost = post;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PostDetailViewModel.class))
        {
            return (T) new PostDetailViewModel(mApplication,mPost);
        }
        throw new IllegalArgumentException("Cannot create Instance for this class");
    }
}
