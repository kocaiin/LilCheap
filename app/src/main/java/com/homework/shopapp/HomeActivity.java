package com.homework.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.homework.shopapp.adapters.ProductAdapter;
import com.homework.shopapp.data.DummyData;
import com.homework.shopapp.models.Product;
import com.homework.shopapp.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private Toolbar toolbar;
    private SearchView searchView;

    private ProductAdapter productAdapter;
    private List<Product> fullProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is logged in
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize product list
        fullProductList = DummyData.getProducts();
        productAdapter = new ProductAdapter(new ArrayList<>(fullProductList));

        // Setup RecyclerView
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);

        // Setup SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Setup cart button
        FloatingActionButton btnViewCart = findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Setup order button
        FloatingActionButton btnViewOrders = findViewById(R.id.btnViewOrders);
        btnViewOrders.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
            startActivity(intent);
        });

        // Setup logout button
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).logout();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void filterProducts(String query) {
        List<Product> filtered = new ArrayList<>();
        for (Product product : fullProductList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(product);
            }
        }
        productAdapter.updateList(filtered);
    }

}
