package com.example.fakestore.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ProductViewModel productViewModel;
    private ProductDatabase productDatabase;
    private RecyclerView productsRecyclerView;

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
        productViewModel = new ProductViewModel();
        productDatabase = ProductDatabase.getInstance(requireContext());
        productsRecyclerView = fragmentProductsBinding.productsRecyclerView;


        //Implementing ViewModel with livedata
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> { //products is the mutableLiveData returned from the ProductViewModel

            switch (products.getStatus()) {
                case SUCCESS:
                    List<ProductModel> productsList = (List<ProductModel>) products.getData();
                    assert productsList != null;
                    fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                    //STORING IN DATABASE
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (int i = 0; i < productsList.size(); i++) {
                            productDatabase.productDao().insertProduct(productsList.get(i));
                        }
                    });

                    ProductAdapter productAdapter = new ProductAdapter(requireContext(), productsList);
                    productsRecyclerView.setLayoutManager(new LinearLayoutManager(productsRecyclerView.getContext()));
                    productsRecyclerView.setAdapter(productAdapter);
                    break;

                case ERROR:

                    //FETCHING FROM DATABASE IN CASE OF FAILED FROM API
                    fetchFromDatabase();
                    break;
            }
        });


        fragmentProductsBinding.floatingActionButton.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment2_to_createProductFragment));
        return fragmentProductsBinding.getRoot();
    }

    private void fetchFromDatabase() {
        //                    productDatabase.productDao().getAllProducts().observe(getViewLifecycleOwner(), productModels -> { // THIS IS DIRECTLY WITHOUT USING VIEWMODEL
        productViewModel.getAllProductsDB(productDatabase).observe(getViewLifecycleOwner(), productsListDB -> {
            if (productsListDB == null || productsListDB.size() <= 0) {
                //                           THIS NAVIGATES THE ERROR FRAGMENT
                Bundle bundle = new Bundle();
                bundle.putSerializable("errorMessage", new Throwable("Unable to fetch from api or cache"));
                Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment2_to_errorFragment, bundle);
            } else {
                fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                ProductAdapter productAdapter_offline = new ProductAdapter(requireContext(), (List<ProductModel>) productsListDB);
                productsRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentProductsBinding.productsRecyclerView.getContext()));
                productsRecyclerView.setAdapter(productAdapter_offline);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentProductsBinding = null;
    }
}