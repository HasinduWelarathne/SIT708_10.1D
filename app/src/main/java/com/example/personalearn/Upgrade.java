package com.example.personalearn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Upgrade extends AppCompatActivity {
    AppCompatButton cardViewPurchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        cardViewPurchaseButton = findViewById(R.id.cardViewPurchaseButton);
        cardViewPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment fragment = new Payment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.fragment_container, fragment);

                transaction.addToBackStack(null);

                transaction.commit();
            }
        });


    }
}