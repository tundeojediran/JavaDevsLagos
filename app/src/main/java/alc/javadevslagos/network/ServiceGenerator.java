package alc.javadevslagos.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dannytee on 06/03/2017.
 */

public class ServiceGenerator {

    // Github API base URL
    public static final String API_BASE_URL = "https://api.github.com/search/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit() {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit;
    }


}
