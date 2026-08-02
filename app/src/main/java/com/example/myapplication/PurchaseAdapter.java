package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> purchaseList;

    public PurchaseAdapter(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase purchase = purchaseList.get(position);
        holder.tvPurchaseProdName.setText(purchase.getProductName());
        holder.tvPurchaseDate.setText("Date: " + purchase.getDate());
        holder.tvPurchaseTotal.setText("GH₵ " + purchase.getTotal());
        holder.tvPurchaseQuantity.setText("Qty: " + purchase.getQuantity());
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView tvPurchaseProdName, tvPurchaseDate, tvPurchaseTotal, tvPurchaseQuantity;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPurchaseProdName = itemView.findViewById(R.id.tvPurchaseProdName);
            tvPurchaseDate = itemView.findViewById(R.id.tvPurchaseDate);
            tvPurchaseTotal = itemView.findViewById(R.id.tvPurchaseTotal);
            tvPurchaseQuantity = itemView.findViewById(R.id.tvPurchaseQuantity);
        }
    }
}