package com.test.testcase2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 3/11/16.
 */
public interface Rest {

    @GET("tracks")
    Call<List<Model>> getData(@Query("client_id") String param1,
                              @Query("offset") int param2,
                              @Query("limit") int param3);
}
