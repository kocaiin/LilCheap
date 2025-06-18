package com.homework.shopapp.data;

import com.homework.shopapp.R;
import com.homework.shopapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("Vintage Camera", "Classic retro camera", 99.99, R.drawable.camera,
                "Vintage-style analog camera with manual controls, 35mm film compatibility, and leather finish for photography enthusiasts."));
        products.add(new Product("Smart Watch", "Waterproof smart watch", 59.49, R.drawable.watch,
                "Fitness-tracking smartwatch with heart rate monitor, sleep analysis, 50m water resistance, and customizable watch faces."));
        products.add(new Product("Sneakers", "Stylish running shoes", 79.00, R.drawable.sneakers,
                "Lightweight athletic sneakers with breathable mesh upper, cushioned soles, and durable rubber outsoles for running and training."));
        products.add(new Product("Wireless Headphones", "Noise cancelling headphones", 120.00, R.drawable.headphone,
                "High-fidelity wireless headphones with active noise cancellation, 30-hour battery life, and ergonomic design."));
        products.add(new Product("Bluetooth Speaker", "Portable wireless speaker", 45.00, R.drawable.speaker,
                "Compact waterproof Bluetooth speaker with 10-hour battery, IPX7 rating, and dual drivers for 360° sound."));
        products.add(new Product("Laptop Stand", "Ergonomic adjustable stand", 34.99, R.drawable.stand,
                "Height-adjustable aluminum laptop stand with multiple angles, improved ventilation, and cable management."));
        products.add(new Product("Desk Lamp", "LED reading lamp", 22.50, R.drawable.lamp,
                "Touch-sensitive LED desk lamp with adjustable brightness, color temperature, and flexible gooseneck arm."));
        products.add(new Product("Backpack", "Water-resistant laptop backpack", 65.00, R.drawable.backpack,
                "Durable water-resistant backpack with padded laptop compartment, multiple storage pockets, and ergonomic shoulder straps."));

        return products;
    }
}

