package petras.bukelis.balticamadeusandroidtask.network.api;

import java.util.List;

import okhttp3.ResponseBody;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.entities.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {
    @GET("/posts")
    Call<List<Post>> getPostList();

    @GET("/users")
    Call<List<User>> getUserList();


}
