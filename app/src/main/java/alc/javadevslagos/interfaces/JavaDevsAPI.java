package alc.javadevslagos.interfaces;

import alc.javadevslagos.models.GitHubAPIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dannytee on 10/03/2017.
 */

public interface JavaDevsAPI {

    /*
       * Retrofit get annotation with URL
       * And method that will return Java Developers in Lagos by page number.
      */
    @GET("users?q=type:users+location:lagos+language:java")
    Call<GitHubAPIResponse> getDevelopers(@Query("page") int page);
}
