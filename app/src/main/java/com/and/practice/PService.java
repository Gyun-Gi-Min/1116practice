package com.and.practice;

import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;

public interface PService {

    @GET("v2/list")
    Call<List<picsumVO>> getPList();
}
