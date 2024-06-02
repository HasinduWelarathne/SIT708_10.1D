package com.example.personalearn;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class QuizModel {

    @SerializedName("results")
    private List<QuizQuestion> results;

    private int userScore;

    public List<QuizQuestion> getResults() {
        return results;
    }

    public void setResults(List<QuizQuestion> results) {
        this.results = results;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public static class QuizQuestion implements Parcelable {
        private String type;
        private String difficulty;
        private String category;
        private String question;
        private String correct_answer;
        private String user_answer;
        private List<String> incorrect_answers;

        // Constructor
        public QuizQuestion() {
        }

        protected QuizQuestion(Parcel in) {
            type = in.readString();
            difficulty = in.readString();
            category = in.readString();
            question = in.readString();
            correct_answer = in.readString();
            user_answer = in.readString();
            incorrect_answers = in.createStringArrayList();
        }

        public static final Creator<QuizQuestion> CREATOR = new Creator<QuizQuestion>() {
            @Override
            public QuizQuestion createFromParcel(Parcel in) {
                return new QuizQuestion(in);
            }

            @Override
            public QuizQuestion[] newArray(int size) {
                return new QuizQuestion[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(type);
            dest.writeString(difficulty);
            dest.writeString(category);
            dest.writeString(question);
            dest.writeString(correct_answer);
            dest.writeString(user_answer);
            dest.writeStringList(incorrect_answers);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getCorrectAnswer() {
            return correct_answer;
        }

        public void setCorrectAnswer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public List<String> getIncorrectAnswers() {
            return incorrect_answers;
        }

        public void setIncorrectAnswers(List<String> incorrect_answers) {
            this.incorrect_answers = incorrect_answers;
        }

        public String getUserAnswer() {
            return user_answer;
        }

        public void setUserAnswer(String user_answer) {
            this.user_answer = user_answer;
        }
    }
}
