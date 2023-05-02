package com.ksbm.ontu.utils;


import static com.ksbm.ontu.api_client.Base_Url.uploadProfile;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Multipart
    @POST(uploadProfile)
    Call<JsonObject> apiUploadPhoto(
            @Part MultipartBody.Part file,
            @Part("API-KEY") RequestBody api_key,
            @Part("userid") RequestBody userid);

}