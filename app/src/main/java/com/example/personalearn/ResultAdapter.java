package com.example.personalearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private Context context;
    private List<QuizModel.QuizQuestion> questions;
    private List<String> userAnswers;
    private List<String> correctAnswers;

    public ResultAdapter(Context context, List<QuizModel.QuizQuestion> questions, List<String> userAnswers, List<String> correctAnswers) {
        this.context = context;
        this.questions = questions;
        this.userAnswers = userAnswers;
        this.correctAnswers = correctAnswers;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        QuizModel.QuizQuestion question = questions.get(position);
        String userAnswer = userAnswers.get(position);
        String correctAnswer = correctAnswers.get(position);

        holder.bind(question, userAnswer, correctAnswer);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView userAnswerTextView;
        TextView correctAnswerTextView;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            userAnswerTextView = itemView.findViewById(R.id.userAnswerTextView);
            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
        }

        void bind(QuizModel.QuizQuestion question, String userAnswer, String correctAnswer) {
            questionTextView.setText("Question: " + question.getQuestion());
            userAnswerTextView.setText("Your Answer: " + userAnswer);
            correctAnswerTextView.setText("Correct Answer: " + correctAnswer);
        }
    }
}