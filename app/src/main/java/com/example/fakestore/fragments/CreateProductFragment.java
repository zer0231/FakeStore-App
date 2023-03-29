package com.example.fakestore.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.fakestore.R;
import com.example.fakestore.ViewModels.ProductViewModel;
import com.example.fakestore.databinding.FragmentCreateProductBinding;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        productViewModel.getCategories().observe(getViewLifecycleOwner(),categories->{
            fragmentCreateProductBinding.categoriesSpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, categories));
            fragmentCreateProductBinding.categoriesSpinner.setSelection(0);
        });

        fragmentCreateProductBinding.createProductBtn.setOnClickListener(view -> {
            String name = Objects.requireNonNull(fragmentCreateProductBinding.productNameEtv.getText()).toString().trim();
            String imageUrl = Objects.requireNonNull(fragmentCreateProductBinding.productImageEtv.getText()).toString().trim();
            String description = Objects.requireNonNull(fragmentCreateProductBinding.productDescriptionEtv.getText()).toString().trim();
            String price = Objects.requireNonNull(fragmentCreateProductBinding.productPriceEtv.getText()).toString().trim();
            String category = fragmentCreateProductBinding.categoriesSpinner.getSelectedItem().toString().trim();

            productViewModel.postProduct(new ProductModel(name, description, category, imageUrl, price),requireContext(),requireView()).observe(getViewLifecycleOwner(),productPosted->{
                Toast.makeText(requireContext(), "Product Added with id: " + productPosted.getId(), Toast.LENGTH_SHORT).show();
            });

//            Call<ProductModel> createProduct = RetrofitClient.getInstance().getApi().createUser(new ProductModel(name, description, category, imageUrl, price));
//            createProduct.enqueue(new Callback<ProductModel>() {
//                @Override
//                public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {
//
//                    ProductModel productModel = response.body();
//                    if (response.isSuccessful()) {
//                        assert productModel != null;
//                        Toast.makeText(requireContext(), "Product Added with id: " + productModel.getId(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(requireContext(), "Failed to add please try again", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("errorMessage", t);
//                    Navigation.findNavController(requireView()).navigate(R.id.action_createProductFragment_to_errorFragment, bundle);
//                }
//            });
        });
        return fragmentCreateProductBinding.getRoot();
    }


}
