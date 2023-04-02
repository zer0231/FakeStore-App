package com.example.fakestore.utilities;

import com.example.fakestore.models.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "https://fakestoreapi.com/";

    @GET("products")
    Call<List<ProductModel>> getProduct();

    @POST("products")
    Call<ProductModel> createProduct(@Body ProductModel productModel); //BODY DECORATOR IS FOR IDENTIFYING THE BODY PART FOR POST

    @GET("products/categories")
    Call<List<String>> getCategories();

}
