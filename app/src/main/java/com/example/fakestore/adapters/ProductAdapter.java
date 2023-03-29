package com.example.fakestore.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakestore.R;
import com.example.fakestore.databinding.CardViewProductsBinding;
import com.example.fakestore.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context parentContext;
    List<ProductModel> productsList;

    public ProductAdapter(Context parentContext, List<ProductModel> productsList) {
        this.parentContext = parentContext;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CardViewProductsBinding.inflate(LayoutInflater.from(parentContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductModel product = productsList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        Picasso.get().load(product.getImageUrl()).error(R.drawable.ic_launcher_foreground).into(holder.cardviewProductsBinding.productImageIv);
        holder.cardviewProductsBinding.productTitleTv.setText(product.getTitle());
        holder.cardviewProductsBinding.productCardView.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_productsFragment2_to_productDetailFragment2, bundle));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardViewProductsBinding cardviewProductsBinding;

        public ViewHolder(@NonNull CardViewProductsBinding cardviewProductsBinding) {
            super(cardviewProductsBinding.getRoot());
            this.cardviewProductsBinding = cardviewProductsBinding;
        }
    }
}
