package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemCustomerBinding;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private OnCustomerClickListener listener;

    public interface OnCustomerClickListener {
        void onView(Customer customer);
        void onDelete(Customer customer);
    }

    public CustomerAdapter(List<Customer> customerList, OnCustomerClickListener listener) {
        this.customerList = customerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomerBinding binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.bind(customer, listener);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void updateList(List<Customer> newList) {
        this.customerList = newList;
        notifyDataSetChanged();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private ItemCustomerBinding binding;

        public CustomerViewHolder(ItemCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Customer customer, OnCustomerClickListener listener) {
            binding.tvCustomerName.setText(customer.getName());
            binding.tvCustomerID.setText("ID: " + customer.getId());
            binding.tvCustomerType.setText(customer.getType());
            binding.tvCustomerStatus.setText(customer.getStatus() == 1 ? "Active" : "Inactive");
            binding.tvCustomerStatus.setTextColor(customer.getStatus() == 1 ? 0xFF4CAF50 : 0xFFF44336);
            binding.tvCustomerPhone.setText(customer.getPhone());
            binding.tvCustomerEmail.setText(customer.getEmail());
            binding.tvRegistrationDate.setText("Registered: " + customer.getRegistrationDate());

            binding.btnView.setOnClickListener(v -> listener.onView(customer));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(customer));
        }
    }
}