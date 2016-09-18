package com.eeyuva.apiservice;


import com.eeyuva.screens.DetailPage.ArticleDetailResponse;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

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
    Call<LoginResponse> getAuthentication(@Query("username") String name, @Query("password") String pass);

    @GET("usermobileregister/")
    Call<LoginResponse> getRegistration(@Query("firstname") String firstName, @Query("lastname") String lastName, @Query("gender") String gender, @Query("email") String email,
                                        @Query("password") String password);

    @GET
    Call<ModuleOrderResponse> getModule(@Url String url);

    @GET
    Call<GetArticleResponse> getArticles(@Url String url);

    @GET
    Call<HotModuleResponse> getHotNews(@Url String url);

    @GET
    Call<ArticleDetailResponse> getArticlesDetails(@Url String url);

    @GET
    Call<SearchResponse> getSearchResponse(@Url String url);

    @GET
    Call<PhotoListResponse> getPhotoList(@Url String url);

    @GET
    Call<PhotoGalleryResponse> getPhotoGalleryList(@Url String url);


//    @POST("driver/deviceid/")
//    Call<FCMRegisterResponse> updateFCMToken(@Body FCMRegisterRequest fcmRegisterRequest);
//
//    @DELETE("driver/deviceid/")
//    Call<FCMRegisterResponse> removeFCMToken();

}
