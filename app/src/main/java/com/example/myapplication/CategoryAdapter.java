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
            binding.tvCategoryName.setText(category.getName());
            binding.tvCategoryCode.setText("Code: " + category.getCode());
            binding.tvCategoryStatus.setText(category.getStatus() == 1 ? "Active" : "Inactive");
            binding.tvCategoryStatus.setTextColor(category.getStatus() == 1 ? 0xFF4CAF50 : 0xFFF44336);
            binding.tvUpdated.setText(category.getUpdatedAt());
            
            if (category.getImage() != null && !category.getImage().isEmpty()) {
                binding.imgCategory.setImageURI(Uri.parse(category.getImage()));
            }

            binding.btnView.setOnClickListener(v -> listener.onView(category));
            binding.btnEdit.setOnClickListener(v -> listener.onEdit(category));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(category));
        }
    }
}