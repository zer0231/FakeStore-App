package com.example.fakestore.viewModels;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fakestore.repositorites.ProductRepository;
import com.example.fakestore.models.ProductModel;


import java.util.List;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<ProductModel>> mutableProductsLiveData;
    private MutableLiveData<List<String>> mutableCategoriesLiveData;
    private MutableLiveData<ProductModel> mutablePostIDLiveData;
    private final ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<List<ProductModel>> getProducts() {
        if (mutableProductsLiveData == null) {
            mutableProductsLiveData = productRepository.requestProducts();
        }
        return mutableProductsLiveData;
    }

    public LiveData<List<String>> getCategories() {
        if (mutableCategoriesLiveData == null) {
            mutableCategoriesLiveData = productRepository.requestCategories();
        }
        return mutableCategoriesLiveData;
    }
    public LiveData<ProductModel> postProduct(ProductModel productModel, Context parentContext, View parentView){
        if(mutablePostIDLiveData == null){
            mutablePostIDLiveData = productRepository.postProduct(productModel,parentContext,parentView);
        }
        return mutablePostIDLiveData;
    }
}
