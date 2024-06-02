package com.example.personalearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    TextView smallTask, name;
    ImageView userImage;
    Button startQuizButton, shareButton, btnUpgrade;
    int categoryId;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        smallTask = findViewById(R.id.smallTask);
        startQuizButton = findViewById(R.id.main_start);
        shareButton = findViewById(R.id.shareButton);
        btnUpgrade = findViewById(R.id.main_upgrade);
        name = findViewById(R.id.text_view_name);
        userImage = findViewById(R.id.userImage);

        // Retrieve category ID from Intent
        categoryId = getIntent().getIntExtra("CategoryID", -1);

        // Fetch user data and set the name
        fetchUserData();

        // Set OnClickListener for the start quiz button
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to navigate to QuizScreen and pass the category ID
                Intent intent = new Intent(MainActivity.this, QuizScreen.class);
                intent.putExtra("CategoryID", categoryId);
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(view -> shareProfile());

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Upgrade.class));
            }
        });
    }

    private void fetchUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String userName = documentSnapshot.getString("Name");
                                name.setText(userName);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private void shareProfile() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bitmap = getScreenShot(rootView);
        shareBitmap(bitmap, "profile.png");
    }

    private Bitmap getScreenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void shareBitmap(Bitmap bitmap, String fileName) {
        String path = getExternalCacheDir() + "/" + fileName;
        File imageFile = new File(path);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            Uri uri = FileProvider.getUriForFile(this, "com.example.personalearn.fileprovider", imageFile);
            if (uri != null) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
