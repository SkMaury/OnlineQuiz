package android.skmaury.com.onlinequiz;

import android.skmaury.com.onlinequiz.Common.Common;
import android.skmaury.com.onlinequiz.Model.Question;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;

public class StartActivity extends AppCompatActivity {

    Button btnPlay;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /* Initializing {@link firebaseDatabase} with appropriate method calls */
        firebaseDatabase = FirebaseDatabase.getInstance();
        questions = firebaseDatabase.getReference("Questions");

        loadQuestions(Common.categoryId);
    }

    private void loadQuestions(String categoryId) {

        /* First, clear list if have old questions */
        if(Common.questionList.size() > 0)
            Common.questionList.clear();

        questions.orderByChild("categoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Question ques = postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        /* For Random question list */
        Collections.shuffle(Common.questionList);
    }
}
