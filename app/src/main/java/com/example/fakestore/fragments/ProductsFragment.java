package com.example.fakestore.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fakestore.R;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.utilities.AppExecutors;
import com.example.fakestore.utilities.ProductDatabase;
import com.example.fakestore.viewModels.ProductViewModel;
import com.example.fakestore.adapters.ProductAdapter;
import com.example.fakestore.databinding.FragmentProductsBinding;

import java.util.List;

public class ProductsFragment extends Fragment {
    private FragmentProductsBinding fragmentProductsBinding;
    
    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false);
        ProductViewModel productViewModel = new ProductViewModel();


        //Implementing ViewModel with livedata
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> { //products is the mutableLiveData returned from the ProductViewModel
            switch (products.getStatus()) {
                case SUCCESS:
                    List<ProductModel> productsList = (List<ProductModel>) products.getData();
                    fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                    //STORING IN DATABASE
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        ProductDatabase productDatabase = ProductDatabase.getInstance(requireContext());
                        for (int i = 0; i < productsList.size(); i++) {
                            productDatabase.productDao().insertProduct(productsList.get(i));
                        }
                    });

                    ProductAdapter productAdapter = new ProductAdapter(requireContext(), productsList);
                    fragmentProductsBinding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentProductsBinding.productsRecyclerView.getContext()));
                    fragmentProductsBinding.productsRecyclerView.setAdapter(productAdapter);
                    break;

                case ERROR:
                    //FETCHING FROM DATABASE IN CASE OF ERROR
                    ProductDatabase productDatabase = ProductDatabase.getInstance(requireContext());
                    productDatabase.productDao().getAllProducts().observe(getViewLifecycleOwner(), productModels -> {
                        if (productModels == null || productModels.size() <= 0) {
//                           THIS NAVIGATES THE ERROR FRAGMENT
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("errorMessage", new Throwable("Unable to fetch from api or cache"));
                            Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment2_to_errorFragment, bundle);
                        } else {
                            fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                            ProductAdapter productAdapter_offline = new ProductAdapter(requireContext(), (List<ProductModel>) productModels);
                            fragmentProductsBinding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentProductsBinding.productsRecyclerView.getContext()));
                            fragmentProductsBinding.productsRecyclerView.setAdapter(productAdapter_offline);
                        }
                    });

                    break;
            }

        });


        fragmentProductsBinding.floatingActionButton.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment2_to_createProductFragment));
        return fragmentProductsBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentProductsBinding = null;
    }
}