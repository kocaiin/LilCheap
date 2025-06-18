package com.homework.shopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.shopapp.R;
import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.utils.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private static final String TAG = "CartAdapter";
    private final List<CartItem> items;
    private final Runnable onCartChanged;

    public CartAdapter(List<CartItem> items, Runnable onCartChanged) {
        this.items = items;
        this.onCartChanged = onCartChanged;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvPrice;
        Button btnInc, btnDec, btnRemove;

        CartViewHolder(@NonNull View v) {
            super(v);
            tvName    = v.findViewById(R.id.tvName);
            tvQty     = v.findViewById(R.id.tvQty);
            tvPrice   = v.findViewById(R.id.tvPrice);
            btnInc    = v.findViewById(R.id.btnInc);
            btnDec    = v.findViewById(R.id.btnDec);
            btnRemove = v.findViewById(R.id.btnRemove);
        }
    }

    @Override
    @NonNull
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        try {
            CartItem ci = items.get(position);
            holder.tvName.setText(ci.getProduct().getName());
            holder.tvQty.setText(String.valueOf(ci.getQuantity()));
            holder.tvPrice.setText(
                    holder.itemView.getContext()
                            .getString(R.string.price_format, ci.getTotalPrice())
            );

            CartManager cartManager = CartManager.getInstance(holder.itemView.getContext());

            holder.btnInc.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                CartItem item = items.get(pos);
                if (cartManager.incrementQuantity(item)) {
                    notifyItemChanged(pos);
                    onCartChanged.run();
                    Log.d(TAG, "Quantity incremented: " + item.getProduct().getName());
                } else {
                    Toast.makeText(v.getContext(), "Max quantity is 99", Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnDec.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                CartItem item = items.get(pos);
                if (cartManager.decrementQuantity(item)) {
                    notifyItemChanged(pos);
                    onCartChanged.run();
                    Log.d(TAG, "Quantity decremented: " + item.getProduct().getName());
                }
            });

            holder.btnRemove.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                CartItem item = items.get(pos);
                cartManager.removeItem(item);
                items.remove(pos);
                notifyItemRemoved(pos);
                onCartChanged.run();
                Log.d(TAG, "Item removed: " + item.getProduct().getName());
            });

        } catch (Exception e) {
            Log.e(TAG, "Bind error: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }
}
