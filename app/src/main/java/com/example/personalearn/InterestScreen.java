package com.example.personalearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalearn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InterestScreen extends AppCompatActivity {

    Button next, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView selectedInterest;

    // Mapping category names to their corresponding IDs
    private static final Map<String, Integer> categoryIds = new HashMap<String, Integer>() {{
        put("General Knowledge", 9);
        put("Entertainment: Books", 10);
        put("Entertainment: Film", 11);
        put("Entertainment: Music", 12);
        // Add more categories here...
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        next = findViewById(R.id.btn_nxt);
        btn1 = findViewById(R.id.general_knowledge);
        btn2 = findViewById(R.id.entertainment_books);
        btn3 = findViewById(R.id.entertainment_film);
        btn4 = findViewById(R.id.entertainment_music);
        // Initialize the rest of the buttons...

        selectedInterest = findViewById(R.id.selectedInterest);

        btn1.setOnClickListener(view -> setSelectedInterest(btn1.getText().toString()));
        btn2.setOnClickListener(view -> setSelectedInterest(btn2.getText().toString()));
        btn3.setOnClickListener(view -> setSelectedInterest(btn3.getText().toString()));
        btn4.setOnClickListener(view -> setSelectedInterest(btn4.getText().toString()));
        // Set click listeners for the rest of the buttons...

        next.setOnClickListener(view -> {
            // Get the selected category name
            String selectedCategory = selectedInterest.getText().toString();
            // Get the corresponding category ID
            Integer categoryId = categoryIds.get(selectedCategory);
            // Create an intent to launch MainActivity and pass the selected category ID
            Intent intent = new Intent(InterestScreen.this, MainActivity.class);
            intent.putExtra("CategoryID", categoryId);
            startActivity(intent);
            finish();
        });
    }

    // Method to set the selected interest
    private void setSelectedInterest(String interest) {
        selectedInterest.setText(interest);
    }
}
