package com.example.makeup.api;

import com.example.makeup.model.Fodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {
    @GET("query.php")
    Call<Fodation> GetItemFodation();
}
