package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set the navigation bar color
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.white));

        BottomNavigationView bottomNavigationView = findViewById(R.id.homenavigation);
        bottomNavigationView.setBackgroundColor(getResources().getColor(android.R.color.white));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.HomeFragment, new HomeCloset())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.addi) {
                showAddItemBottomSheet();
                return true;
            } else if (item.getItemId() == R.id.closet) {
                selectedFragment = new Settings(); // Show Settings for "Closet"
            } else if (item.getItemId() == R.id.outfits) {
                selectedFragment = new HomeCloset(); // Show HomeCloset for "Outfits"

            } else if (item.getItemId() == R.id.settings) {
                showSettingsBottomMenu(); // Show bottom menu for "Settings"
                return true;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.HomeFragment, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

    private void showAddItemBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(view);

        TextView addClothes = view.findViewById(R.id.add_items);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView create = view.findViewById(R.id.creat);

        addClothes.setOnClickListener(v -> {
            showAddOptionsBottomSheet();
            bottomSheetDialog.dismiss();
        });

        create.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Canvas.class);
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void showAddOptionsBottomSheet() {
        BottomSheetDialog addOptionsDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.add_options_sheet, null);
        addOptionsDialog.setContentView(view);

        TextView takePhoto = view.findViewById(R.id.take_photo);
        TextView addFromGallery = view.findViewById(R.id.add_from_gallery);
        TextView cancel = view.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
            addOptionsDialog.dismiss();
        });

        addFromGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
            addOptionsDialog.dismiss();
        });

        cancel.setOnClickListener(v -> addOptionsDialog.dismiss());

        addOptionsDialog.show();
    }

    private void showCreateLookbookBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.create_lookbook_sheet, null);
        bottomSheetDialog.setContentView(view);

        EditText lookbookNameEditText = view.findViewById(R.id.lookbook_name);
        TextView createLookbookButton = view.findViewById(R.id.create_lookbook_button);
        TextView cancel = view.findViewById(R.id.cancel);

        createLookbookButton.setOnClickListener(v -> {
            String lookbookName = lookbookNameEditText.getText().toString().trim();
            if (!lookbookName.isEmpty()) {
                Intent intent = new Intent(Home.this, LookbookActivity.class);
                intent.putExtra("lookbookName", lookbookName);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            } else {
                Toast.makeText(Home.this, "Please enter a lookbook name", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void showSettingsBottomMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.menubottom, null);

        NavigationView navigationView = bottomSheetView.findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.log_out) {
                logOut();
            } else if (item.getItemId() == R.id.delete) {
                deleteAccount();
            }
            bottomSheetDialog.dismiss();
            return true;
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void logOut() {
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteAccount() {
        // Logic to delete the account
        // For example, deleting shared preferences, user data, etc.
        // After deletion, navigate back to MainActivity
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(Home.this, NewItem.class);

            if (requestCode == 1) {
                if (data != null && data.getExtras() != null) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    intent.putExtra("imageBitmap", photo);
                }
            } else if (requestCode == 2) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    intent.putExtra("imageUri", selectedImageUri.toString());
                }
            }

            startActivity(intent);
        }
    }
}