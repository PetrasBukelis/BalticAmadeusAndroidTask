package petras.bukelis.balticamadeusandroidtask.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.db.AppDao;
import petras.bukelis.balticamadeusandroidtask.db.AppDatabase;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.entities.User;
import petras.bukelis.balticamadeusandroidtask.network.api.PostApi;
import petras.bukelis.balticamadeusandroidtask.network.services.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepository {
    private MutableLiveData<List<Post>> allPosts;
    private MutableLiveData<List<User>> allUsers;
    private static PostApi postApi;

    public APIRepository(Application application) {
    }

    public LiveData<List<Post>> getPosts() {
        if (allPosts == null) {
            Log.d("DATAFLOWapiPOSTS", ("API FAILED"));
            allPosts = new MutableLiveData<List<Post>>();
            loadPosts();
        }
        return allPosts;
    }
    public LiveData<List<User>> getUsers() {
        if (allUsers == null) {
            Log.d("DATAFLOWapiUSERS", ("API FAILED"));
            allUsers = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return allUsers;
    }


    private void loadPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        postApi = retrofit.create(PostApi.class);
        Call<List<Post>> call = postApi.getPostList();


        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                //finally we are setting the list to our MutableLiveData
                allPosts.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                allPosts.setValue(null);
            }
        });
    }
    private void loadUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        postApi = retrofit.create(PostApi.class);
        Call<List<User>> call = postApi.getUserList();


        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                //finally we are setting the list to our MutableLiveData
                allUsers.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                allUsers.setValue(null);
            }
        });
    }
}
