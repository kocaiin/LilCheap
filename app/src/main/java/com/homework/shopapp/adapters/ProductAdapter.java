package com.homework.shopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.homework.shopapp.R;
import com.homework.shopapp.models.Product;

import java.util.List;

import androidx.annotation.NonNull;

import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.utils.CartManager;

import android.widget.Toast;
import android.widget.Button;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<Product> productList;

    public ProductAdapter(List<Product> products) {
        this.productList = products;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, desc, price;
        Button btnAddCart;

        public ProductViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivProductImage);
            name = itemView.findViewById(R.id.tvProductName);
            desc = itemView.findViewById(R.id.tvProductDesc);
            price = itemView.findViewById(R.id.tvProductPrice);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
        }
    }

    @Override
    @NonNull
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.name.setText(p.getName());
        holder.desc.setText(p.getDescription());

        String formattedPrice = String.format(holder.price.getContext().getString(R.string.price_format), p.getPrice());
        holder.price.setText(formattedPrice);

        holder.image.setImageResource(p.getImageResId());

        holder.btnAddCart.setOnClickListener(v -> {
            CartManager.getInstance(v.getContext())
                    .addToCart(new CartItem(p));
            Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
        });

        // 🔹 Click the item to open product detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), com.homework.shopapp.ProductDetailActivity.class);
            intent.putExtra("name", p.getName());
            intent.putExtra("desc", p.getDescription());
            intent.putExtra("price", p.getPrice());
            intent.putExtra("image", p.getImageResId());
            intent.putExtra("fullDesc", p.getFullDescription());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // 🔹 ADD THIS for search/filter support
    public void updateList(List<Product> newList) {
        productList.clear();
        productList.addAll(newList);
        notifyDataSetChanged();
    }
}
