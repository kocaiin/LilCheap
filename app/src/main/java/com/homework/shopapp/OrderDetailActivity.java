package com.homework.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.models.Order;
import com.homework.shopapp.utils.OrderManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView tvOrderId, tvOrderStatus, tvOrderTime, tvLogistics, tvTotalPrice;
    private LinearLayout layoutItems;
    private Button btnEvaluate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvLogistics = findViewById(R.id.tvLogistics);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        layoutItems = findViewById(R.id.layoutItems);
        btnEvaluate = findViewById(R.id.btnEvaluate);

        Order order = OrderManager.getCurrentOrder();
        if (order == null) return;

        tvOrderId.setText("Order ID: " + order.getOrderId());
        tvOrderStatus.setText("Status: " + order.getStatus());

        String formattedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date(order.getTimestamp()));
        tvOrderTime.setText("Time: " + formattedTime);

        tvLogistics.setText("Logistics: XX物流 - 未发货");

        double total = 0;
        layoutItems.removeAllViews();

        for (CartItem item : order.getItems()) {
            total += item.getQuantity() * item.getProduct().getPrice();

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 8, 0, 8);

            ImageView img = new ImageView(this);
            img.setImageResource(item.getProduct().getImageResId());
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(100, 100);
            img.setLayoutParams(imgParams);

            TextView tv = new TextView(this);
            tv.setText(item.getProduct().getName() + " × " + item.getQuantity());
            tv.setPadding(16, 0, 0, 0);

            row.addView(img);
            row.addView(tv);
            layoutItems.addView(row);
        }

        tvTotalPrice.setText("Total: $" + String.format("%.2f", total));

        // ✅ Evaluate button opens EvaluateActivity
        btnEvaluate.setOnClickListener(v -> {
            Intent intent = new Intent(OrderDetailActivity.this, EvaluateActivity.class);
            startActivity(intent);
        });
    }
}
