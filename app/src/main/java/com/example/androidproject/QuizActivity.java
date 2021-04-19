package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Question> questions;
    private  int index =0;
    Question Allquestion;
    TextView question;
    TextView option_1;
    TextView option_2;
    TextView option_3;
    TextView option_4;
    TextView questionCounter;
    TextView timer;
    CountDownTimer countDownTimer;

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
        questions.add(new Question("What is earth?","Planet","Sun","Human","Car","Planet"));
        questions.add(new Question("What is Samosa?","Planet","Food","Human","Car","Food"));
        questions.add(new Question("What is tfaha?","Planet","Sun","Human","Car","Planet"));
        resetTimer();
        setNextQuestion();
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
            textView.setBackgroundResource(R.drawable.option_right);
        }else{
            showAnswer();
            textView.setBackgroundResource(R.drawable.option_wrong);
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