package com.homework.shopapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homework.shopapp.models.CartItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "shoppe_cart";
    private static final String KEY_CART = "key_cart";
    private static final int MAX_QUANTITY = 99;
    private static CartManager instance;
    private List<CartItem> cart;
    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    private CartManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadCart();
    }

    public static CartManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new CartManager(ctx.getApplicationContext());
        }
        return instance;
    }

    private void loadCart() {
        try {
            String json = prefs.getString(KEY_CART, null);
            if (json != null) {
                Type type = new TypeToken<ArrayList<CartItem>>(){}.getType();
                cart = gson.fromJson(json, type);
            } else {
                cart = new ArrayList<>();
            }
        } catch (Exception e) {
            Log.e("CartManager", "Error loading cart: " + e.getMessage());
            cart = new ArrayList<>();
        }
    }

    public void saveCart() {
        try {
            prefs.edit()
                    .putString(KEY_CART, gson.toJson(cart))
                    .apply();
        } catch (Exception e) {
            Log.e("CartManager", "Error saving cart: " + e.getMessage());
        }
    }

    public void addToCart(CartItem item) {
        for (CartItem ci : cart) {
            if (ci.getProduct().getName().equals(item.getProduct().getName())) {
                if (ci.getQuantity() < MAX_QUANTITY) {
                    ci.setQuantity(ci.getQuantity() + 1);
                    saveCart();
                }
                return;
            }
        }
        cart.add(item);
        saveCart();
    }

    public boolean updateQuantity(CartItem item, int newQuantity) {
        if (newQuantity < 1 || newQuantity > MAX_QUANTITY) {
            return false;
        }
        
        for (CartItem ci : cart) {
            if (ci.getProduct().getName().equals(item.getProduct().getName())) {
                ci.setQuantity(newQuantity);
                saveCart();
                return true;
            }
        }
        return false;
    }

    public boolean incrementQuantity(CartItem item) {
        for (CartItem ci : cart) {
            if (ci.getProduct().getName().equals(item.getProduct().getName())) {
                if (ci.getQuantity() < MAX_QUANTITY) {
                    ci.setQuantity(ci.getQuantity() + 1);
                    saveCart();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean decrementQuantity(CartItem item) {
        for (CartItem ci : cart) {
            if (ci.getProduct().getName().equals(item.getProduct().getName())) {
                if (ci.getQuantity() > 1) {
                    ci.setQuantity(ci.getQuantity() - 1);
                    saveCart();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public List<CartItem> getCartItems() {
        return cart;
    }

    public void removeItem(CartItem item) {
        try {
            if (item != null && cart != null) {
                boolean removed = cart.remove(item);
                if (removed) {
                    saveCart();
                    Log.d("CartManager", "Item removed successfully: " + item.getProduct().getName());
                } else {
                    Log.w("CartManager", "Item not found in cart: " + item.getProduct().getName());
                }
            }
        } catch (Exception e) {
            Log.e("CartManager", "Error removing item: " + e.getMessage());
        }
    }

    public void clearCart() {
        try {
            if (cart != null) {
                cart.clear();
                saveCart();
                Log.d("CartManager", "Cart cleared successfully");
            }
        } catch (Exception e) {
            Log.e("CartManager", "Error clearing cart: " + e.getMessage());
        }
    }
}
