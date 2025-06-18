package com.homework.shopapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homework.shopapp.models.CartItem;
import com.homework.shopapp.models.Order;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderManager {
    private static final String PREF_NAME = "shoppe_orders";
    private static final String KEY_ORDERS = "key_orders";

    private static OrderManager instance;
    private final SharedPreferences prefs;
    private final Gson gson = new Gson();
    private List<Order> orders;

    // ✅ 临时记录当前选中的订单
    private static Order currentOrder;

    private OrderManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadOrders();
    }

    public static synchronized OrderManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrderManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadOrders() {
        try {
            String json = prefs.getString(KEY_ORDERS, null);
            if (json != null) {
                Type type = new TypeToken<ArrayList<Order>>(){}.getType();
                orders = gson.fromJson(json, type);
            } else {
                orders = new ArrayList<>();
            }
        } catch (Exception e) {
            Log.e("OrderManager", "Error loading orders: " + e.getMessage());
            orders = new ArrayList<>();
        }
    }

    private void saveOrders() {
        try {
            prefs.edit()
                    .putString(KEY_ORDERS, gson.toJson(orders))
                    .apply();
        } catch (Exception e) {
            Log.e("OrderManager", "Error saving orders: " + e.getMessage());
        }
    }

    public void placeOrder(List<CartItem> items) {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        Order newOrder = new Order(orderId, items, "Pending", System.currentTimeMillis());
        orders.add(0, newOrder);
        saveOrders();
    }

    public List<Order> getOrders() {
        return orders;
    }

    // ✅ 新增：设置和获取当前选中的订单
    public static void setCurrentOrder(Order order) {
        currentOrder = order;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }
}
