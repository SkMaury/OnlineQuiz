package android.skmaury.com.onlinequiz;

import android.content.Intent;
import android.skmaury.com.onlinequiz.Common.Common;
import android.skmaury.com.onlinequiz.Model.QuestionScore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoneActivity extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore, getTxtResultQuestion;
    ProgressBar progressBar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference question_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        firebaseDatabase = FirebaseDatabase.getInstance();
        question_score = firebaseDatabase.getReference();

        txtResultScore = findViewById(R.id.txtTotalScore);
        getTxtResultQuestion = findViewById(R.id.txtTotalQuestion);
        progressBar = findViewById(R.id.doneProgressBar);
        btnTryAgain = findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /* Get data from bundle and set to the view */
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTxtResultQuestion.setText(String.format("PASSED : %d / %d", correctAnswer, totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            /* Upload data to FireBase real time database */
            question_score.child(String.format("%s_%s", Common.currentUser.getUserName(),
                    Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUserName(),
                            Common.categoryId),
                            Common.currentUser.getUserName(),
                            String.valueOf(score)));
        }
    }
}
