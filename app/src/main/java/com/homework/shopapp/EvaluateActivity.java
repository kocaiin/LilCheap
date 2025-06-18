// EvaluateActivity.java
package com.homework.shopapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.homework.shopapp.models.Order;
import com.homework.shopapp.utils.OrderManager;

public class EvaluateActivity extends AppCompatActivity {

    private TextView tvOrderId;
    private RatingBar ratingBar;
    private EditText etComment;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        tvOrderId = findViewById(R.id.tvOrderId);
        ratingBar = findViewById(R.id.ratingBar);
        etComment = findViewById(R.id.etComment);
        btnSubmit = findViewById(R.id.btnSubmit);

        Order order = OrderManager.getCurrentOrder();
        if (order != null) {
            tvOrderId.setText("Order #" + order.getOrderId());
        }

        btnSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String comment = etComment.getText().toString().trim();

            // You can save this evaluation locally or print it
            Toast.makeText(this, "Rated " + rating + " stars\nComment: " + comment, Toast.LENGTH_LONG).show();

            finish(); // Close the evaluation page
        });
    }
}
