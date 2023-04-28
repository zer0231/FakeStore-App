package com.example.fakestore.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fakestore.adapters.ViewPagerAdapter;
import com.example.fakestore.databinding.ActivityViewPagerBinding;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.viewModels.ProductViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private ActivityViewPagerBinding binding;
    String TAG = "ViewPagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewPagerBinding.inflate(getLayoutInflater());
        TabLayout tabLayout = binding.tablayout;
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.getProducts().observe(this, products -> {
            switch (products.getStatus()) {
                case SUCCESS:
                    List<ProductModel> productsList = (List<ProductModel>) products.getData();
                    ViewPagerAdapter adapter = new ViewPagerAdapter(this, productsList);
                    binding.viewpager.setAdapter(adapter);
                    binding.viewpager.setOffscreenPageLimit(5);
                    new TabLayoutMediator(binding.tablayout, binding.viewpager, (tab, position) -> {
                        tab.setText(productsList.get(position).getTitle());
                        Log.d(TAG, productsList.get(position).getTitle());
                    }).attach();
                    break;
                case LOADING:
                    Log.d(TAG, "LOADING");
                    break;
                case ERROR:
                    Log.d(TAG, "ERROR" + products.getError().toString());
                    break;
            }
        });
        binding.tablayout.setContentDescription("test");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, tab.getPosition() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setContentView(binding.getRoot());
    }

}