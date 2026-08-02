package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemProductBinding;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onEdit(Product product);
        void onDelete(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product, OnProductClickListener listener) {
            binding.tvProductName.setText(product.getName());
            binding.tvProductID.setText("ID: " + product.getId());
            binding.tvProductPrice.setText("GH₵ " + product.getPrice());
            binding.tvProductQuantity.setText("Qty: " + product.getQuantity());

            // Set stock status
            if (product.getQuantity() <= 0) {
                binding.tvStockStatus.setText("Out of Stock");
                binding.tvStockStatus.setTextColor(binding.getRoot().getContext().getResources().getColor(android.R.color.holo_red_dark));
            } else if (product.getQuantity() <= 5) {
                binding.tvStockStatus.setText("Low Stock");
                binding.tvStockStatus.setTextColor(binding.getRoot().getContext().getResources().getColor(android.R.color.holo_orange_dark));
            } else {
                binding.tvStockStatus.setText("In Stock");
                binding.tvStockStatus.setTextColor(binding.getRoot().getContext().getResources().getColor(android.R.color.holo_green_dark));
            }

            binding.btnEdit.setOnClickListener(v -> listener.onEdit(product));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(product));
        }
    }
}