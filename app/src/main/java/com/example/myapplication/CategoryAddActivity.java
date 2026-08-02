package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.databinding.ActivityCategoryAddBinding;
import com.google.android.material.snackbar.Snackbar;

public class CategoryAddActivity extends AppCompatActivity {

    private ActivityCategoryAddBinding binding;
    private DatabaseHelper dbHelper;
    private String selectedImageUri = "";
    private Category categoryToEdit = null;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri.toString();
                    binding.imgCategory.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("EDIT_CATEGORY")) {
            categoryToEdit = (Category) getIntent().getSerializableExtra("EDIT_CATEGORY");
            if (categoryToEdit != null) {
                binding.tvTitle.setText("Edit Category");
                binding.etName.setText(categoryToEdit.getName());
                binding.etCode.setText(categoryToEdit.getCode());
                binding.etDesc.setText(categoryToEdit.getDescription());
                binding.switchStatus.setChecked(categoryToEdit.getStatus() == 1);
                selectedImageUri = categoryToEdit.getImage();
                if (selectedImageUri != null && !selectedImageUri.isEmpty()) {
                    binding.imgCategory.setImageURI(Uri.parse(selectedImageUri));
                }
            }
        }

        binding.btnSelectImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        binding.btnCancel.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(v -> saveCategory());
    }

    private void saveCategory() {
        String name = binding.etName.getText().toString().trim();
        String code = binding.etCode.getText().toString().trim();
        String desc = binding.etDesc.getText().toString().trim();
        int status = binding.switchStatus.isChecked() ? 1 : 0;

        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Name and Code are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (categoryToEdit == null) {
            // Check for duplicates
            if (dbHelper.isCategoryDuplicate(name, code)) {
                Toast.makeText(this, "Category Name or Code already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.addCategory(name, code, desc, selectedImageUri, status);
            if (success) {
                Snackbar.make(binding.getRoot(), "Category added", Snackbar.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show();
            }
        } else {
            boolean success = dbHelper.updateCategory(categoryToEdit.getId(), name, code, desc, selectedImageUri, status);
            if (success) {
                Snackbar.make(binding.getRoot(), "Category updated", Snackbar.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}