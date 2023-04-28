package com.example.fakestore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakestore.R;
import com.example.fakestore.databinding.CardViewProductsBinding;
import com.example.fakestore.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductModel> products;

    public ViewPagerAdapter(Context context, List<ProductModel> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerAdapter.ViewHolder(CardViewProductsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
        ProductModel product = products.get(position);
        holder.cardViewProductBinding.productTitleTv.setText(product.getTitle());
        Picasso.get().load(product.getImageUrl()).error(R.drawable.ic_launcher_foreground).into(holder.cardViewProductBinding.productImageIv);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardViewProductsBinding cardViewProductBinding;

        public ViewHolder(@NonNull CardViewProductsBinding cardViewProductBinding) {
            super(cardViewProductBinding.getRoot());
            this.cardViewProductBinding = cardViewProductBinding;
        }
    }
}
