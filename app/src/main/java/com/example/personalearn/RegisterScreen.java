package com.example.personalearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends AppCompatActivity {

    ImageView userPhoto;
    Button btnGetImage, register;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    TextInputEditText name, password, phone, email;
    TextView login;

    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        btnGetImage = findViewById(R.id.btnGetImage);
        userPhoto = findViewById(R.id.userPhoto);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.text_view_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterScreen.this, LoginActivity.class));
                finish();
            }
        });

        //Init Firebase
        mAuth = FirebaseAuth.getInstance();
        //Init Firebase firestore
        db = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        final int PICK_IMAGE = 100;
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK && reqCode == 100){
            final Uri imageUri = data.getData();
            uri = imageUri.toString();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                uri = selectedImage.toString();
                userPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void signUpUser(){
        String emailAdd = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(emailAdd, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name.getText().toString().trim());
                user.put("Email", emailAdd);
                user.put("Password", pass);
                user.put("Phone", phone.getText().toString().trim());
                user.put("UID", mAuth.getCurrentUser().getUid());
                user.put("URI", uri);

                db.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterScreen.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterScreen.this, InterestScreen.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterScreen.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
