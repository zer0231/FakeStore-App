package com.example.fakestore.repositorites;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.ProductDatabase;
import com.example.fakestore.utilities.RetrofitClient;
import com.example.fakestore.utilities.StateLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    public ProductRepository() {
    }


    public StateLiveData<List<ProductModel>> requestProducts() {
        StateLiveData<List<ProductModel>> productMutableLiveData = new StateLiveData<>();
        Call<List<ProductModel>> productsCall = RetrofitClient.getInstance().getApi().getProduct();
        productsCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                productMutableLiveData.setSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                productMutableLiveData.postError(t);
            }
        });
        return productMutableLiveData;
    }

    public StateLiveData<List<String>> requestCategories() {
        StateLiveData<List<String>> categoriesMutableLiveData = new StateLiveData<>();
        Call<List<String>> categories = RetrofitClient.getInstance().getApi().getCategories();
        categories.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                categoriesMutableLiveData.setSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                categoriesMutableLiveData.postError(t);
            }
        });
        return categoriesMutableLiveData;
    }

    public StateLiveData<ProductModel> postProduct(ProductModel productModel) {
        StateLiveData<ProductModel> postProductMutableData = new StateLiveData<>();
        Call<ProductModel> createProduct = RetrofitClient.getInstance().getApi().createProduct(productModel);
        createProduct.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {
                postProductMutableData.setSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                postProductMutableData.postError(t);
            }
        });
        return postProductMutableData;
    }

    public LiveData<List<ProductModel>> fetchProductsDB(ProductDatabase productDatabase) {
// USE LIVEDATA FOR ASYNC TASK
        return productDatabase.productDao().getAllProducts();

    }

}
