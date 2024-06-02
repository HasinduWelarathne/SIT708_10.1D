package com.example.personalearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ResultScreen extends AppCompatActivity {

    private TextView scoreSummary;
    private Button takeAnotherQuizButton;
    private RecyclerView questionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        // Initialize UI components
        scoreSummary = findViewById(R.id.scoreSummary);
        takeAnotherQuizButton = findViewById(R.id.takeAnotherQuizButton);
        questionRecyclerView = findViewById(R.id.questionRecyclerView);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve data from intent
        int score = getIntent().getIntExtra("Score", 0);
        List<QuizModel.QuizQuestion> questions = (List<QuizModel.QuizQuestion>) getIntent().getSerializableExtra("Questions");
        List<String> userAnswers = getIntent().getStringArrayListExtra("UserAnswers");
        List<String> correctAnswers = getIntent().getStringArrayListExtra("CorrectAnswers");

        // Set up RecyclerView adapter
        ResultAdapter resultAdapter = new ResultAdapter(this, questions, userAnswers, correctAnswers);
        questionRecyclerView.setAdapter(resultAdapter);

        // Display score summary
        scoreSummary.setText("Your score: " + score + "/" + questions.size());

        // Take another quiz button click listener
        takeAnotherQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the quiz screen or perform desired action
                finish();
            }
        });
    }
}

