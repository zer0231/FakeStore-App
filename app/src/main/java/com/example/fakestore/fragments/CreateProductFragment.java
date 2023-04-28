package com.example.fakestore.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.fakestore.R;
import com.example.fakestore.viewModels.ProductViewModel;
import com.example.fakestore.databinding.FragmentCreateProductBinding;
import com.example.fakestore.models.ProductModel;

import java.util.List;
import java.util.Objects;

public class CreateProductFragment extends Fragment {
    private FragmentCreateProductBinding fragmentCreateProductBinding;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCreateProductBinding = FragmentCreateProductBinding.inflate(inflater, container, false);
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            switch (categories.getStatus()) {
                case SUCCESS:
                    fragmentCreateProductBinding.createProductBtn.setEnabled(true);
                    List<String> categoriesGet = (List<String>) categories.getData();
                    fragmentCreateProductBinding.categoriesSpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, categoriesGet));
                    fragmentCreateProductBinding.categoriesSpinner.setSelection(0);
                    break;
                case ERROR:
                    fragmentCreateProductBinding.createProductBtn.setText(R.string.not_connect_internet);
                    Toast.makeText(requireContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        fragmentCreateProductBinding.createProductBtn.setOnClickListener(view -> {
            String name = Objects.requireNonNull(fragmentCreateProductBinding.productNameEtv.getText()).toString().trim();
            String imageUrl = Objects.requireNonNull(fragmentCreateProductBinding.productImageEtv.getText()).toString().trim();
            String description = Objects.requireNonNull(fragmentCreateProductBinding.productDescriptionEtv.getText()).toString().trim();
            String price = Objects.requireNonNull(fragmentCreateProductBinding.productPriceEtv.getText()).toString().trim();
            String category = fragmentCreateProductBinding.categoriesSpinner.getSelectedItem() == null ? "None" : fragmentCreateProductBinding.categoriesSpinner.getSelectedItem().toString().trim();
            productViewModel.postProduct(new ProductModel(name, description, category, imageUrl, price)).observe(getViewLifecycleOwner(), productPosted -> {
                switch (productPosted.getStatus()) {
                    case SUCCESS:
                        ProductModel productModel = (ProductModel) productPosted.getData();

                        if (productModel != null) {
                            Toast.makeText(requireContext(), "Product Added with id: " + productModel.getId(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case ERROR:
                        assert productPosted.getError() != null;
                        Toast.makeText(requireContext(), "Failed to add \n" + productPosted.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            });

        });
        return fragmentCreateProductBinding.getRoot();
    }


}
