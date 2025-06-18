package com.homework.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.homework.shopapp.models.Product;
import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.utils.CartManager;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESC = "desc";
    public static final String EXTRA_PRICE = "price";
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_FULL_DESC = "fullDesc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        TextView tvFullDesc = findViewById(R.id.tvDetailFullDesc);
        Button btnAdd = findViewById(R.id.btnDetailAddCart);
        Button btnGoToStore = findViewById(R.id.btnGoToStore); // Visit store button

        // Get product data from Intent
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String desc = getIntent().getStringExtra(EXTRA_DESC);
        double price = getIntent().getDoubleExtra(EXTRA_PRICE, 0.0);
        int image = getIntent().getIntExtra(EXTRA_IMAGE, 0);
        String fullDesc = getIntent().getStringExtra(EXTRA_FULL_DESC);

        // Display product info
        tvName.setText(name);
        tvDesc.setText(desc);
        tvPrice.setText(String.format(getString(R.string.price_format), price));
        tvFullDesc.setText(fullDesc);
        ivImage.setImageResource(image);

        // Add to cart button click
        btnAdd.setOnClickListener(v -> {
            Product product = new Product(name, desc, price, image, fullDesc);
            CartManager.getInstance(this).addToCart(new CartItem(product));
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });

        // Visit store button click
        btnGoToStore.setOnClickListener(v -> {
            int storeId = getStoreIdByProduct(name);
            Intent intent = new Intent(ProductDetailActivity.this, StoreDetailActivity.class);
            intent.putExtra(StoreDetailActivity.EXTRA_STORE_ID, storeId);
            startActivity(intent);
        });
    }

    // Store ID by product name
    private int getStoreIdByProduct(String productName) {
        switch (productName) {
            case "Vintage Camera":
            case "Smart Watch":
            case "Sneakers":
                return 1;
            default:
                return 2;
        }
    }
}
