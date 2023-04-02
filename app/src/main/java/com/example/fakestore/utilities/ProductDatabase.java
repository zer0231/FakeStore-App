package com.example.fakestore.utilities;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fakestore.models.ProductModel;



@Database(entities ={ProductModel.class}, exportSchema = false, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    public static ProductDatabase instance;
    public static final String DB_NAME = "Product_DB";

    public static synchronized ProductDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, ProductDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract ProductDao productDao();
}
