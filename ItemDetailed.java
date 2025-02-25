package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ItemDetailed extends AppCompatActivity {

    private ImageView imageView;
    private Button btnCategory, btnSubcategory, btnColors, btnSize;
    private Uri imageUri;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);

        // Find views
        imageView = findViewById(R.id.imageView);
        btnCategory = findViewById(R.id.btnseason);
        btnSubcategory = findViewById(R.id.btnoccasion);
        btnColors = findViewById(R.id.btntags);
        btnSize = findViewById(R.id.btnnote);
        Button backButton = findViewById(R.id.back);
        ImageView editMenu = findViewById(R.id.editmenu);

        // Back button
        backButton.setOnClickListener(v -> finish());

        // Get the data passed from FragmentItems
        imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        position = getIntent().getIntExtra("position", -1);

        // Load the image and details
        imageView.setImageURI(imageUri);
        loadDetails(position);

        // Setup edit menu button
        editMenu.setOnClickListener(v -> showBottomMenu());
    }

    private void loadDetails(int position) {
        // Load the actual details based on the position
        btnCategory.setText(NewItem.getCategory(position));
        btnSubcategory.setText(NewItem.getSubcategory(position));
        btnColors.setText(NewItem.getItemColor(position));
        btnSize.setText(NewItem.getSize(position));

        // Disable buttons to make them non-clickable
        btnCategory.setClickable(false);
        btnSubcategory.setClickable(false);
        btnColors.setClickable(false);
        btnSize.setClickable(false);
    }

    private void showBottomMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.edit_option_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView editItem = bottomSheetView.findViewById(R.id.edititem);
        TextView deleteItem = bottomSheetView.findViewById(R.id.delete);
        TextView cancel = bottomSheetView.findViewById(R.id.cancel);

        editItem.setOnClickListener(v -> {
            editItem();
            bottomSheetDialog.dismiss();
        });

        deleteItem.setOnClickListener(v -> {
            deleteItem();
            bottomSheetDialog.dismiss();
        });

        cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void editItem() {
        Intent intent = new Intent(this, NewItem.class);
        intent.putExtra("imageUri", imageUri.toString());
        intent.putExtra("position", position);
        intent.putExtra("isEdit", true); // edit
        startActivity(intent);
        finish();
    }

    private void deleteItem() {
        NewItem.removeItem(position);
        Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}