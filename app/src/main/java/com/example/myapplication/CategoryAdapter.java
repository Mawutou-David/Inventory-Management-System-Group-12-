package com.example.myapplication;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemCategoryBinding;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onView(Category category);
        void onEdit(Category category);
        void onDelete(Category category);
    }

    public CategoryAdapter(List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateList(List<Category> newList) {
        this.categoryList = newList;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category, OnCategoryClickListener listener) {
            DatabaseHelper dbHelper = new DatabaseHelper(binding.getRoot().getContext());
            int productCount = dbHelper.getProductCountForCategory(category.getName());
            int totalStock = dbHelper.getTotalStockForCategory(category.getName());

            binding.tvCategoryName.setText(category.getName());
            binding.tvCategoryCode.setText("Code: " + category.getCode());
            binding.tvCategoryStatus.setText(category.getStatus() == 1 ? "Active" : "Inactive");
            binding.tvCategoryStatus.setTextColor(category.getStatus() == 1 ? 0xFF4CAF50 : 0xFFF44336);
            binding.tvNumProducts.setText("Products: " + productCount);
            binding.tvTotalStock.setText("Total Stock: " + totalStock);

            // Format date if it's a timestamp
            String updatedAt = category.getUpdatedAt();
            try {
                long timestamp = Long.parseLong(updatedAt);
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                updatedAt = sdf.format(new java.util.Date(timestamp));
            } catch (NumberFormatException e) {
                // Not a timestamp, use as is
            }
            binding.tvUpdated.setText(updatedAt);
            
            // Set icon based on category name/code
            int iconRes = R.drawable.baseline_category_24;
            String name = category.getName().toLowerCase();
            if (name.contains("smartphone") || name.contains("phone")) iconRes = R.drawable.phone;
            else if (name.contains("inventory")) iconRes = R.drawable.baseline_inventory_24;
            else if (name.contains("account")) iconRes = R.drawable.baseline_account_circle_24;
            
            binding.imgCategory.setImageResource(iconRes);

            binding.btnView.setOnClickListener(v -> listener.onView(category));
            binding.btnEdit.setOnClickListener(v -> listener.onEdit(category));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(category));
        }
    }
}