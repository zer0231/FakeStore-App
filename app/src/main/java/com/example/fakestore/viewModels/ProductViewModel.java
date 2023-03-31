package com.example.fakestore.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fakestore.repositorites.ProductRepository;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.ProductDatabase;
import com.example.fakestore.utilities.StateLiveData;

import java.util.List;

// TODO: lOAD FROM DB AFTER ALL THE DATA HAS BEEN REQUESTED
public class ProductViewModel extends ViewModel {
    private StateLiveData<List<ProductModel>> mutableProductsLiveData;
    private LiveData<List<ProductModel>> mutableProductsLiveOfflineData;
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
//THE DATABASE VIEW-MODEL DIRECTLY FETCH FROM REPOSITORY
//    public LiveData<List<ProductModel>> getAllProducts_offline(ProductDatabase productDatabase) {
//        if (mutableProductsLiveOfflineData == null) {
//            mutableProductsLiveOfflineData = productRepository.fetchProductsDB(productDatabase);
//        }
//        return mutableProductsLiveOfflineData;
//    }

    public StateLiveData<List<String>> getCategories() {
        if (mutableCategoriesLiveData == null) {
            mutableCategoriesLiveData = productRepository.requestCategories();
        }
        return mutableCategoriesLiveData;
    }

    public StateLiveData<ProductModel> postProduct(ProductModel productModel) {
        if (mutablePostIDLiveData == null) {
            mutablePostIDLiveData = productRepository.postProduct(productModel);
        }
        return mutablePostIDLiveData;
    }
}
