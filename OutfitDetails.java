package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OutfitDetails extends AppCompatActivity {

    private ConstraintLayout parentLayout;
    private Button btnSeason, btnOccasion, btnNote, btnTags, createFitButton;
    private String selectedNote = "";
    private List<CanvasItem> canvasItems;

    private static final List<String> seasonList = Arrays.asList("Winter", "Autumn", "Spring", "Summer", "All year round", "No season");
    private static final List<String> occasionList = Arrays.asList("Afternoon tea", "Business lunch", "Casual", "Casual lunch", "Cinema", "Club", "Dance", "Date lunch", "Date night", "Dating", "Meet up", "Party", "Wedding", "Work", "Other");
    private static final List<String> tagsList = Arrays.asList("Dating", "Night out", "Straight sleeve", "Oversized top", "Cropped length", "Long sleeve", "Casual", "Minimal", "Sport", "Footwear", "Velvet", "No tags");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfitdetails);

        parentLayout = findViewById(R.id.outfitDetailsLayout);
        btnSeason = findViewById(R.id.btnseason);
        btnOccasion = findViewById(R.id.btnoccasion);
        btnNote = findViewById(R.id.btnnote);
        btnTags = findViewById(R.id.btntags);
        createFitButton = findViewById(R.id.creatfit);

        canvasItems = getIntent().getParcelableArrayListExtra("canvasItems");

        if (canvasItems == null || canvasItems.isEmpty()) {
            Log.e("OutfitDetails", "No items received in intent");
            return;
        }

        Log.d("OutfitDetails", "Received items: " + canvasItems.size());

        for (CanvasItem item : canvasItems) {
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(item.getUri());
            adjustImageViewSizeAndPosition(imageView, item);
            parentLayout.addView(imageView);
        }

        btnSeason.setOnClickListener(v -> showBottomSheet("Select Season", seasonList, btnSeason));
        btnOccasion.setOnClickListener(v -> showBottomSheet("Select Occasion", occasionList, btnOccasion));
        btnNote.setOnClickListener(v -> showNoteBottomSheet());
        btnTags.setOnClickListener(v -> showBottomSheet("Select Tags", tagsList, btnTags));

        createFitButton.setOnClickListener(v -> {
            Uri imageUri = captureCombinedImage();
            if (imageUri != null) {
                Log.d("OutfitDetails", "Image URI: " + imageUri.toString());
                Intent intent = new Intent(this, HomeCloset.class);
                intent.putExtra("combinedImageUri", imageUri.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                Log.e("OutfitDetails", "Failed to create outfit image");
                Toast.makeText(OutfitDetails.this, "Failed to create outfit image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adjustImageViewSizeAndPosition(ImageView imageView, CanvasItem item) {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                (int) item.getWidth(),
                (int) item.getHeight()
        );

        params.leftMargin = (int) item.getLeft();
        params.topMargin = (int) item.getTop();

        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);

        imageView.setLayoutParams(params);
    }

    private void showBottomSheet(String title, List<String> items, Button button) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView tvTitle = bottomSheetView.findViewById(R.id.tvTitle);
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recyclerView);
        Button btnAddYourOwn = bottomSheetView.findViewById(R.id.btnAddYourOwn);

        tvTitle.setText(title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomSheetAdapter adapter = new BottomSheetAdapter(items, item -> {
            bottomSheetDialog.dismiss();
            button.setText(item);
        });
        recyclerView.setAdapter(adapter);

        btnAddYourOwn.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            // Handle add your own functionality
        });

        bottomSheetDialog.show();
    }

    private void showNoteBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.note_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        EditText etNote = bottomSheetView.findViewById(R.id.etNote);
        Button btnSave = bottomSheetView.findViewById(R.id.btnSave);
        Button btnCancel = bottomSheetView.findViewById(R.id.btnCancel);

        if (!selectedNote.isEmpty()) {
            etNote.setText(selectedNote);
        }

        btnSave.setOnClickListener(v -> {
            selectedNote = etNote.getText().toString();
            btnNote.setText(selectedNote);
            bottomSheetDialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private Uri captureCombinedImage() {
        Bitmap bitmap = Bitmap.createBitmap(parentLayout.getWidth(), parentLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        parentLayout.draw(canvas);

        File file = new File(getCacheDir(), "combined_image.png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.d("OutfitDetails", "Image saved successfully");
            return FileProvider.getUriForFile(this, "com.example.closetifiy_finalproject.fileprovider", file);
        } catch (IOException e) {
            Log.e("OutfitDetails", "Error saving image", e);
            return null;
        }
    }
}