package com.homework.shopapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.shopapp.models.Order;
import com.homework.shopapp.utils.OrderManager;
import com.homework.shopapp.adapters.OrderAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView rvOrders;
    private OrderAdapter orderAdapter;
    private OrderManager orderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderManager = OrderManager.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbarOrder);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderManager.getOrders());
        rvOrders.setAdapter(orderAdapter);

        // Log existing orders
        for (Order order : orderManager.getOrders()) {
            Log.d("DEBUG_ORDER", "OrderId: " + order.getOrderId() + ", Time: " + order.getTimestamp());
        }

        Button btnFilter = findViewById(R.id.btnFilter);
        Button btnRefresh = findViewById(R.id.btnRefresh);

        btnFilter.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    OrderActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        long selectedStart = getStartOfDayMillis(calendar.getTimeInMillis());
                        long selectedEnd = getEndOfDayMillis(calendar.getTimeInMillis());

                        Log.d("FILTER_CHECK", "Start: " + selectedStart + ", End: " + selectedEnd);

                        List<Order> filtered = new ArrayList<>();
                        for (Order order : orderManager.getOrders()) {
                            long orderTime = order.getTimestamp();
                            if (orderTime >= selectedStart && orderTime <= selectedEnd) {
                                filtered.add(order);
                            }
                        }

                        orderAdapter.updateOrders(filtered);

                        if (filtered.isEmpty()) {
                            Toast.makeText(OrderActivity.this, "No orders found for this date", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderActivity.this, "Filtered " + filtered.size() + " orders", Toast.LENGTH_SHORT).show();
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        btnRefresh.setOnClickListener(v -> {
            List<Order> latest = orderManager.getOrders();
            orderAdapter.updateOrders(latest);
            Toast.makeText(this, "Orders refreshed", Toast.LENGTH_SHORT).show();
        });
    }

    private long getStartOfDayMillis(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long getEndOfDayMillis(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
