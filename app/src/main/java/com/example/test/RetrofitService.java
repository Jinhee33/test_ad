package com.example.test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/OrderList")
    Call<PageListModel> getList(@Query("menuName") String menuName);
}
