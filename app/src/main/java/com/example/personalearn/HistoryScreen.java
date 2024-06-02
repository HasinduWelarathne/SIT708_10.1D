package com.example.personalearn;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

    public class HistoryScreen extends AppCompatActivity {

        private RecyclerView historyRecyclerView;
        private HistoryAdapter historyAdapter;
        private List<QuizModel> quizResults = new ArrayList<>();
        private FirebaseFirestore db;
        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history_screen);

            // Initialize Firebase
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();

            // Initialize UI components
            historyRecyclerView = findViewById(R.id.historyRecyclerView);
            historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Fetch quiz history
            fetchQuizHistory();
        }

        private void fetchQuizHistory() {
            String userId = mAuth.getCurrentUser().getUid();
            db.collection("quiz_results")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot document : task.getResult()) {
                                QuizModel result = document.toObject(QuizModel.class);
                                quizResults.add(result);
                            }
                            historyAdapter = new HistoryAdapter(HistoryScreen.this, quizResults);
                            historyRecyclerView.setAdapter(historyAdapter);
                        }
                    });
        }
    }
