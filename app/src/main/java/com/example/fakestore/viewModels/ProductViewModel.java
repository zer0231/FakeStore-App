package com.example.fakestore.viewModels;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fakestore.repositorites.ProductRepository;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.StateLiveData;


import java.util.List;

public class ProductViewModel extends ViewModel {
    private StateLiveData<List<ProductModel>> mutableProductsLiveData;
    private StateLiveData<List<String>> mutableCategoriesLiveData;
    private StateLiveData<ProductModel> mutablePostIDLiveData;
    private final ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public StateLiveData<List<ProductModel>> getProducts() {
        if (mutableProductsLiveData == null) {
            mutableProductsLiveData = productRepository.requestProducts();
        }
        return mutableProductsLiveData;
    }

    public StateLiveData<List<String>> getCategories() {
        if (mutableCategoriesLiveData == null) {
            mutableCategoriesLiveData = productRepository.requestCategories();
        }
        return mutableCategoriesLiveData;
    }
    public StateLiveData<ProductModel> postProduct(ProductModel productModel){
        if(mutablePostIDLiveData == null){
            mutablePostIDLiveData = productRepository.postProduct(productModel);
        }
        return mutablePostIDLiveData;
    }
}
