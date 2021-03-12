package petras.bukelis.balticamadeusandroidtask.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.entities.User;
import petras.bukelis.balticamadeusandroidtask.network.api.PostApi;
import petras.bukelis.balticamadeusandroidtask.network.services.RetroFitResponseListener;
import petras.bukelis.balticamadeusandroidtask.network.services.RetrofitService;
import petras.bukelis.balticamadeusandroidtask.repository.DBRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {
    private DBRepository repository;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<List<User>> allUsersapi;

    public UserViewModel(@NonNull Application application) {
        super(application);
        allUsersapi = new MutableLiveData<>();
        repository = new DBRepository(application);
        allUsers = repository.getAllUsers();
    }
    public MutableLiveData<List<User>> getUserListObserver() {
        return allUsersapi;

    }
    public void loadUsers(RetroFitResponseListener retrofitResponseListener) {
        PostApi apiService = RetrofitService.getRetroClient().create(PostApi.class);
        Call<List<User>> call = apiService.getUserList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    retrofitResponseListener.onSuccess();
                    allUsersapi.postValue(response.body());
                }
                else
                    retrofitResponseListener.onFailure();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                allUsersapi.postValue(null);
                retrofitResponseListener.onFailure();
            }
        });
    }



    public void insert(User user)
    {
        repository.insert(user);
    }
    public void update(User user)
    {
        repository.update(user);
    }
    public void delete(User user)
    {
        repository.delete(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
}
