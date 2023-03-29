package com.example.fakestore.repositorites;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.fakestore.R;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    final MutableLiveData<List<ProductModel>> productMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<List<String>> categoriesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductModel> postProductMutableData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> requestProducts() {
        Call<List<ProductModel>> productsCall = RetrofitClient.getInstance().getApi().getProduct();
        productsCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                productMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

            }
        });
        return productMutableLiveData;
    }

    public MutableLiveData<List<String>> requestCategories(){
        Call<List<String>> categories = RetrofitClient.getInstance().getApi().getCategories();
        categories.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                categoriesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    return categoriesMutableLiveData;
    }

    public MutableLiveData<ProductModel> postProduct(ProductModel productModel, Context parentContext, View parentView) {
        Call<ProductModel> createProduct=  RetrofitClient.getInstance().getApi().createUser(productModel);
        createProduct.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {

//                ProductModel productModel = response.body();
                postProductMutableData.setValue(response.body());
//                if (response.isSuccessful()) {
//                    assert productModel != null;
//                    Toast.makeText(parentContext, "Product Added with id: " + productModel.getId(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(parentContext, "Failed to add please try again", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("errorMessage", t);
                Navigation.findNavController(parentView).navigate(R.id.action_createProductFragment_to_errorFragment, bundle);
            }
        });
        return  postProductMutableData;
    }
}
