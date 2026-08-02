package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;

    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.tvCustomerName.setText(customer.getFullName());
        holder.tvCustomerID.setText("ID: " + customer.getId());
        holder.tvCustomerPhone.setText("Phone: " + customer.getPhoneNumber());
        holder.tvCustomerEmail.setText("Email: " + customer.getEmail());
        holder.tvRegDate.setText("Reg. Date: " + customer.getRegistrationDate());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvCustomerID, tvCustomerPhone, tvCustomerEmail, tvRegDate;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerID = itemView.findViewById(R.id.tvCustomerID);
            tvCustomerPhone = itemView.findViewById(R.id.tvCustomerPhone);
            tvCustomerEmail = itemView.findViewById(R.id.tvCustomerEmail);
            tvRegDate = itemView.findViewById(R.id.tvRegDate);
        }
    }
}
