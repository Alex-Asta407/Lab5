package com.example.lab5;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IRatesService {
    @GET
    Call<ResponseBody> getRatesResponse(@Url String url);
}
