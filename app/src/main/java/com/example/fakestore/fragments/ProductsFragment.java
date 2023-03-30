package com.example.fakestore.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fakestore.R;
import com.example.fakestore.models.ProductModel;
import com.example.fakestore.models.StateDataModel;
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
                    fragmentProductsBinding.circularProgressIndicator.setVisibility(View.GONE);
                    ProductAdapter productAdapter = new ProductAdapter(requireContext(), (List<ProductModel>) products.getData());
                    fragmentProductsBinding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(fragmentProductsBinding.productsRecyclerView.getContext()));
                    fragmentProductsBinding.productsRecyclerView.setAdapter(productAdapter);
                    break;
                case ERROR:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("errorMessage", products.getError());
                    Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment2_to_errorFragment, bundle);
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