package com.example.personalearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<QuizModel> quizResults;

    public HistoryAdapter(Context context, List<QuizModel> quizResults) {
        this.context = context;
        this.quizResults = quizResults;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        QuizModel result = quizResults.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView scoreTextView;
        TextView questionsTextView;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            questionsTextView = itemView.findViewById(R.id.questionsTextView);
        }

        void bind(QuizModel result) {
            scoreTextView.setText("Score: " + result.getUserScore() + "/" + result.getResults().size());
            StringBuilder questionsBuilder = new StringBuilder();
            for (int i = 0; i < result.getResults().size(); i++) {
                QuizModel.QuizQuestion question = result.getResults().get(i);
                questionsBuilder.append("Q: ").append(question.getQuestion()).append("\n");
                questionsBuilder.append("Your Answer: ").append(question.getUserAnswer()).append("\n");
                questionsBuilder.append("Correct Answer: ").append(question.getCorrectAnswer()).append("\n\n");
            }
            questionsTextView.setText(questionsBuilder.toString());
        }
    }
}
