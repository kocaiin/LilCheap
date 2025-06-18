package com.homework.shopapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.homework.shopapp.R;
import com.homework.shopapp.models.Order;
import com.homework.shopapp.models.CartItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public void updateOrders(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.bind(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrderNumber;
        private final TextView tvOrderStatus;
        private final LinearLayout layoutThumbnails;
        private final Context context;

        public OrderViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            layoutThumbnails = itemView.findViewById(R.id.layoutThumbnails);
        }

        public void bind(Order order) {
            tvOrderNumber.setText("Order #" + order.getOrderId());
            tvOrderStatus.setText("Status: " + order.getStatus());

            layoutThumbnails.removeAllViews();

            for (CartItem item : order.getItems()) {
                ImageView imageView = new ImageView(context);

                int size = (int) context.getResources().getDimension(R.dimen.thumbnail_size);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                params.setMargins(8, 0, 8, 0);
                imageView.setLayoutParams(params);
                imageView.setImageResource(item.getProduct().getImageResId());

                layoutThumbnails.addView(imageView);
            }

            // ✅ 点击订单跳转详情页
            itemView.setOnClickListener(v -> {
                com.homework.shopapp.utils.OrderManager.setCurrentOrder(order); // 设置当前订单
                android.content.Intent intent = new android.content.Intent(context, com.homework.shopapp.OrderDetailActivity.class);
                context.startActivity(intent);
            });
        }

    }
}
