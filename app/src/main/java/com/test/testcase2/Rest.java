package com.test.testcase2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 3/11/16.
 */
public interface Rest {


    @GET("tracks?client_id=734fc03dc9ee26f4c1a4841d0b6f00e4")
    Call<List<Model>> getData();
}
