package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    private  int index =0;
    int correctAnswers =0;
    Question Allquestion;
    TextView question;
    TextView option_1;
    TextView option_2;
    TextView option_3;
    TextView option_4;
    TextView questionCounter;
    TextView timer;
    CountDownTimer countDownTimer;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = (TextView) findViewById(R.id.question);
        option_1 = (TextView) findViewById(R.id.option_1);
        option_2 = (TextView) findViewById(R.id.option_2);
        option_3 = (TextView) findViewById(R.id.option_3);
        option_4 = (TextView) findViewById(R.id.option_4);
        questionCounter = findViewById(R.id.questionCounter);
        timer = findViewById(R.id.timer);

        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        final String catId = getIntent().getStringExtra("catId");
        Random random = new Random();
        final int rand = random.nextInt(5);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5) {
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Question question = snapshot.toObject(Question.class);
                                    questions.add(question);
                                }
                            setNextQuestion();


                        }
                    });
                }else{
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }
            }
        });



        resetTimer();

    }


    void resetTimer(){
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }



    void setNextQuestion(){
        if (countDownTimer!= null){
            countDownTimer.cancel();
        }

        countDownTimer.start();
        if (index < questions.size()){
            questionCounter.setText(String.format("%d/%d",(index+1),questions.size()));
            Allquestion = questions.get(index);

            question.setText(Allquestion.getQuestion());
            option_1.setText(Allquestion.getOption1());
            option_2.setText(Allquestion.getOption2());
            option_3.setText(Allquestion.getOption3());
            option_4.setText(Allquestion.getOption4());

        }
    }
    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(Allquestion.getAnswer())){
            correctAnswers++;
            textView.setBackgroundResource(R.drawable.option_right);
        }else{
            showAnswer();
            textView.setBackgroundResource(R.drawable.option_wrong);
        }
    }

    void showAnswer(){
        if (Allquestion.getAnswer().equals(option_1.getText().toString())){
            option_1.setBackgroundResource(R.drawable.option_right);
        }else if(Allquestion.getAnswer().equals(option_2.getText().toString())){
            option_2.setBackgroundResource(R.drawable.option_right);
        }else if(Allquestion.getAnswer().equals(option_3.getText().toString())){
            option_3.setBackgroundResource(R.drawable.option_right);
        }else if(Allquestion.getAnswer().equals(option_3.getText().toString())){
            option_3.setBackgroundResource(R.drawable.option_right);
        }
    }

    void reset(){
        option_1.setBackgroundResource(R.drawable.option_unselected);
        option_2.setBackgroundResource(R.drawable.option_unselected);
        option_3.setBackgroundResource(R.drawable.option_unselected);
        option_4.setBackgroundResource(R.drawable.option_unselected);
    }

    public void OnClick(View view){
        switch (view.getId()){
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if (countDownTimer!= null){
                    countDownTimer.cancel();
                }
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.nextBtn:
                reset();
                if (index < questions.size()){
                    index++;
                    setNextQuestion();
                }else{
                    Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}