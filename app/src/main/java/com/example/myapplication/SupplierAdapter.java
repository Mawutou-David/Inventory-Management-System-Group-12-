package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {

    private List<Supplier> supplierList;

    public SupplierAdapter(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supplier, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {
        Supplier supplier = supplierList.get(position);
        holder.tvSupplierName.setText(supplier.getName());
        holder.tvSupplierContact.setText("Contact: " + supplier.getContact());
        holder.tvSupplierAddress.setText("Address: " + supplier.getAddress());
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public static class SupplierViewHolder extends RecyclerView.ViewHolder {
        TextView tvSupplierName, tvSupplierContact, tvSupplierAddress;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName);
            tvSupplierContact = itemView.findViewById(R.id.tvSupplierContact);
            tvSupplierAddress = itemView.findViewById(R.id.tvSupplierAddress);
        }
    }
}