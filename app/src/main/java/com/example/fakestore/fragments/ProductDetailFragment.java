package com.example.fakestore.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fakestore.R;
import com.example.fakestore.databinding.FragmentProductDetailBinding;
import com.example.fakestore.models.ProductModel;
import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {


    FragmentProductDetailBinding fragmentProductDetailBinding;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert getArguments() != null;
        ProductModel product = (ProductModel) getArguments().getSerializable("product");
        String price = product.getPrice() + " $";
        // Inflate the layout for this fragment
        fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater, container, false);
        //Picasso for loading image from url
        Picasso.get().load(product.getImageUrl()).error(R.drawable.ic_launcher_foreground).into(fragmentProductDetailBinding.productImageIv);
        fragmentProductDetailBinding.productCategoryTv.setText(product.getCategory());
        fragmentProductDetailBinding.productPriceTv.setText(price);
        fragmentProductDetailBinding.productDescriptionTv.setText(product.getDescription());
        fragmentProductDetailBinding.productTitleTv.setText(product.getTitle());
        return fragmentProductDetailBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentProductDetailBinding = null;
    }
}