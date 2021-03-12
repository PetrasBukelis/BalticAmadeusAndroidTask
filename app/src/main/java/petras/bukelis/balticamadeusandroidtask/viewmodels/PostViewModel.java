package petras.bukelis.balticamadeusandroidtask.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.network.api.PostApi;
import petras.bukelis.balticamadeusandroidtask.network.services.RetroFitResponseListener;
import petras.bukelis.balticamadeusandroidtask.network.services.RetrofitService;
import petras.bukelis.balticamadeusandroidtask.repository.APIRepository;
import petras.bukelis.balticamadeusandroidtask.repository.DBRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends AndroidViewModel {
    private DBRepository repository;
    private APIRepository apiRepository;
    private LiveData<List<Post>> allPosts;
    private MutableLiveData<List<Post>> allPostsapi;
    public PostViewModel(@NonNull Application application) {
        super(application);
        allPostsapi = new MutableLiveData<>();
        repository = new DBRepository(application);
        allPosts = repository.getAllPosts();
    }

    public MutableLiveData<List<Post>> getPostListObserver() {
        return allPostsapi;

    }
    public void loadPosts(RetroFitResponseListener retrofitResponseListener) {

        PostApi apiService = RetrofitService.getRetroClient().create(PostApi.class);
        Call<List<Post>> call = apiService.getPostList();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    retrofitResponseListener.onSuccess();
                    allPostsapi.postValue(response.body());
                }
                else
                    retrofitResponseListener.onFailure();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                allPostsapi.postValue(null);
                retrofitResponseListener.onFailure();
            }
        });
    }


    public void insert(Post post)
    {
        repository.insert(post);
    }
    public void update(Post post)
    {
        repository.update(post);
    }
    public void delete(Post post)
    {
        repository.delete(post);
    }

    public LiveData<List<Post>> getAllPosts() {
        return allPosts;
    }
    public MutableLiveData<List<Post>> getAllPostsAPI() {
        return allPostsapi;
    }
}
