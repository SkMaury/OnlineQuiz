package android.skmaury.com.onlinequiz.Model;

/**
 * Created by kurosaki on 28/02/18.
 */

public class QuestionScore {
    private String Question_Score;
    private String user, Score;

    public QuestionScore() {
    }

    public QuestionScore(String question_Score, String user, String score) {
        Question_Score = question_Score;
        this.user = user;
        Score = score;
    }

    public String getQuestion_Score() {
        return Question_Score;
    }

    public void setQuestion_Score(String question_Score) {
        Question_Score = question_Score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
