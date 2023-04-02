package com.example.fakestore.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Product_table")
public class ProductModel implements Serializable { //To make this object serializable
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("category")
    private String category;
    @SerializedName("image")
    private String imageUrl;
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long id;
    @SerializedName("price")
    private String price;
    public ProductModel(String title, String description, String category, String imageUrl, long id, String price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.id = id;
        this.price = price;
    }
    @Ignore
    public ProductModel() {

    }
    @Ignore
    public ProductModel(String title, String description, String category, String imageUrl, String price) { //Without ID because this is used for posting
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
