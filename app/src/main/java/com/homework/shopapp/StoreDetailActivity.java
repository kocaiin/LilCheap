package com.homework.shopapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StoreDetailActivity extends AppCompatActivity {

    public static final String EXTRA_STORE_ID = "store_id";

    private TextView tvStoreName, tvStoreIntro, tvStoreRating, tvStoreQual;
    private ImageView ivStoreImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreIntro = findViewById(R.id.tvStoreIntro);
        tvStoreRating = findViewById(R.id.tvStoreRating);
        tvStoreQual = findViewById(R.id.tvStoreQualifications);
        ivStoreImage = findViewById(R.id.ivStoreImage);

        int storeId = getIntent().getIntExtra(EXTRA_STORE_ID, 1);

        if (storeId == 1) {
            tvStoreName.setText("Tech Gear Store");
            tvStoreIntro.setText("We provide the best quality cameras, watches, and sneakers for tech-savvy people.");
            ivStoreImage.setImageResource(R.drawable.store1); // add store1.png in drawable
        } else {
            tvStoreName.setText("Gadget Hub");
            tvStoreIntro.setText("Your go-to hub for headphones, speakers, lamps, and backpacks.");
            ivStoreImage.setImageResource(R.drawable.store2); // add store2.png in drawable
        }

        // Random rating
        Random rand = new Random();
        float rating = 3.5f + rand.nextFloat() * 1.5f; // Range: 3.5 to 5.0
        tvStoreRating.setText(String.format("Rating: %.1f ★", rating));

        tvStoreQual.setText("Qualifications:\n- Licensed Retailer\n- 5+ Years Experience\n- Excellent Customer Reviews");
    }
}
