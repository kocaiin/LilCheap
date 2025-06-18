package com.homework.shopapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.homework.shopapp.adapters.CartAdapter;
import com.homework.shopapp.utils.CartManager;
import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.utils.OrderManager;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    private RecyclerView rvCartItems;
    private TextView tvTotal;
    private Toolbar toolbar;
    private CartManager cartManager;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Log.d(TAG, "onCreate called");

        // Initialize CartManager
        cartManager = CartManager.getInstance(this);

        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Handle toolbar back button
        toolbar.setNavigationOnClickListener(v -> {
            Log.d(TAG, "Toolbar back clicked");
            finish();
        });

        // Setup RecyclerView
        rvCartItems = findViewById(R.id.rvCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with a *copy* of current cart items
        List<CartItem> currentItems = new ArrayList<>(cartManager.getCartItems());
        cartAdapter = new CartAdapter(currentItems, this::updateTotal);
        rvCartItems.setAdapter(cartAdapter);

        // Setup total display
        tvTotal = findViewById(R.id.tvTotal);
        updateTotal();

        // Setup checkout button
        findViewById(R.id.btnCheckout).setOnClickListener(v -> {
            Log.d(TAG, "Checkout clicked");
            onCheckout();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        updateTotal(); // only update total, do not recreate adapter
    }

    private void updateTotal() {
        try {
            double total = 0;
            for (CartItem item : cartManager.getCartItems()) {
                total += item.getProduct().getPrice() * item.getQuantity();
            }
            tvTotal.setText(String.format(Locale.US, "Total: $%.2f", total));
            Log.d(TAG, "Total updated: " + total);
        } catch (Exception e) {
            Log.e(TAG, "Error updating total: " + e.getMessage());
        }
    }

    private void onCheckout() {
        try {
            List<CartItem> orderItems = new ArrayList<>(cartManager.getCartItems());
            OrderManager.getInstance(this).placeOrder(orderItems); // ✅ Pass context

            cartManager.clearCart();
            cartAdapter.clearItems();
            updateTotal();

            Toast.makeText(this, "Order placed!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error during checkout: " + e.getMessage());
        }
    }



    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed called");
        super.onBackPressed(); // call super, no need to override
    }
}
