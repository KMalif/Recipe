package com.example.resep.utilities

import com.example.resep.models.Post
import com.example.resep.models.User
import com.example.resep.responses.WrappedListResponse
import com.example.resep.responses.WrappedResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("api/post")
    fun all(@Header("Authorization")token : String): Call<WrappedListResponse<Post>>

    @FormUrlEncoded
    @POST("api/post")
    fun create(@Header("Authorization")token: String, @Field("title")title: String, @Field("content")content : String): Call<WrappedResponse<Post>>

    @GET("api/post/{id}")
    fun find(@Header("Authorization")token: String, @Path("id")id :String):Call<WrappedResponse<Post>>

    @DELETE("api/post/{id}")
    fun delete(@Header("Authorization")token: String, @Path("id")id: String):Call<WrappedResponse<Post>>

    @FormUrlEncoded
    @PUT("api/post/{id}")
    fun update(@Header("Authorization")token: String, @Path("id")id: String, @Field("title")title: String, @Field("content")content: String):Call<WrappedResponse<Post>>

    @FormUrlEncoded
    @POST("api/login")
    fun login(@Field("email")email : String, @Field("password")password : String):Call<WrappedResponse<User>>

    @POST("api/register")
    fun register(@Field("name")name : String, @Field("email")email: String, @Field("password")password: String): Call<WrappedResponse<User>>
}