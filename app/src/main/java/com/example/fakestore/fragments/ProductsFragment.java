package com.example.fakestore.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fakestore.R;
import com.example.fakestore.viewModels.ProductViewModel;
import com.example.fakestore.adapters.ProductAdapter;
import com.example.fakestore.databinding.FragmentProductsBinding;

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
        ProductViewModel productViewModel = new ProductViewModel();
        fragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false);
        //Implementing ViewModel with livedata
        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> { //products is the mutableLiveData returned from the ProductViewModel
            if(products!=null)
            {
                fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                ProductAdapter productAdapter = new ProductAdapter(requireContext(), products);
                fragmentProductsBinding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentProductsBinding.productsRecyclerView.getContext()));
                fragmentProductsBinding.productsRecyclerView.setAdapter(productAdapter);
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