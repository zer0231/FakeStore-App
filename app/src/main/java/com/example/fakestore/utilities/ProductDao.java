package com.example.fakestore.utilities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fakestore.models.ProductModel;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product_table WHERE id = :product_id")
    ProductModel getProductById(int product_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(ProductModel product);

    @Query("SELECT * FROM Product_table")
    LiveData<List<ProductModel>> getAllProducts();

}
