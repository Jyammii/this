package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class Canvas extends AppCompatActivity {

    private ConstraintLayout canvasLayout;
    private List<Uri> selectedItems = new ArrayList<>();
    private Button doneButton;
    private Button addOutfitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas);

        canvasLayout = findViewById(R.id.canvasLayout);
        doneButton = findViewById(R.id.donebtn);
        addOutfitButton = findViewById(R.id.addoutfit);

        Intent intent = getIntent();
        if (intent != null && intent.getParcelableArrayListExtra("selectedItems") != null) {
            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
        }

        if (selectedItems != null) {
            for (Uri uri : selectedItems) {
                ImageView imageView = new ImageView(this);
                imageView.setImageURI(uri);
                imageView.setTag(uri); // Add Uri as tag
                imageView.setOnTouchListener(new MultiTouchListener(this)); // Pass the context here
                canvasLayout.addView(imageView);
            }
        }

        addOutfitButton.setOnClickListener(v -> {
            Intent selectIntent = new Intent(Canvas.this, SelectFits.class);
            startActivityForResult(selectIntent, 1);
        });

        doneButton.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(Canvas.this, OutfitDetails.class);
            ArrayList<CanvasItem> canvasItems = new ArrayList<>();
            for (int i = 0; i < canvasLayout.getChildCount(); i++) {
                View view = canvasLayout.getChildAt(i);
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    Uri uri = (Uri) imageView.getTag();
                    if (uri != null) {
                        CanvasItem canvasItem = new CanvasItem(
                                uri,
                                imageView.getX(),
                                imageView.getY(),
                                imageView.getWidth(),
                                imageView.getHeight()
                        );
                        canvasItems.add(canvasItem);
                    } else {
                        Log.e("Canvas", "ImageView tag is null, cannot create CanvasItem.");
                    }
                }
            }

            if (canvasItems.isEmpty()) {
                Log.e("Canvas", "No CanvasItems created, not starting OutfitDetails.");
                return;
            }

            detailsIntent.putParcelableArrayListExtra("canvasItems", canvasItems);
            Log.d("Canvas", "Starting OutfitDetails with " + canvasItems.size() + " items.");
            startActivity(detailsIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            List<Uri> newSelectedItems = data.getParcelableArrayListExtra("selectedItems");
            if (newSelectedItems != null) {
                selectedItems.addAll(newSelectedItems);
                for (Uri uri : newSelectedItems) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageURI(uri);
                    imageView.setTag(uri); // Add Uri as tag
                    imageView.setOnTouchListener(new MultiTouchListener(this));
                    canvasLayout.addView(imageView);
                }
            }
        }
    }
}