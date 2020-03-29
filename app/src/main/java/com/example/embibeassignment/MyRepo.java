package com.example.embibeassignment;

import com.example.embibeassignment.model.Movies;
import com.example.embibeassignment.model.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyRepo {

    @GET("/3/movie/now_playing?api_key=133f54c2d291c13439447bde8de344c6")
    Call<Movies> getUsers(@QueryMap Map<String, String> params);

    @GET("/3/search/movie?api_key=133f54c2d291c13439447bde8de344c6")
    Call<Movies> getQuery(@QueryMap Map<String, String> params);
}
