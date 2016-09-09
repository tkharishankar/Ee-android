package com.eeyuva.apiservice;



import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by hari on 22/6/16.
 */
public interface Api {
    @GET("user_mlogin/")
    Call<LoginResponse> getAuthentication(@Query("username") String name,@Query("password") String pass);

    @GET("usermobileregister/")
    Call<LoginResponse> getRegistration(@Query("firstname") String firstName,@Query("lastname") String lastName,@Query("gender") String gender,@Query("email") String email,
                                        @Query("password") String password);
    @GET
    Call<ModuleOrderResponse> getModule(@Url String url);
    @GET
    Call<GetArticleResponse> getArticles(@Url String url);


//    @POST("driver/deviceid/")
//    Call<FCMRegisterResponse> updateFCMToken(@Body FCMRegisterRequest fcmRegisterRequest);
//
//    @DELETE("driver/deviceid/")
//    Call<FCMRegisterResponse> removeFCMToken();

}
