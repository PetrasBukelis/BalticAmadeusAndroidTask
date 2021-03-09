package petras.bukelis.balticamadeusandroidtask.network.services;

import petras.bukelis.balticamadeusandroidtask.network.api.PostApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static String BASE_URL = "https://jsonplaceholder.typicode.com";

    private static Retrofit retrofit;
    public static Retrofit getRetroClient() {

        if(retrofit == null ) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
