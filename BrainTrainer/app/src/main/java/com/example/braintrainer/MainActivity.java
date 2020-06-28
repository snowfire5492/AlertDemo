package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    CountDownTimer timer;
    Random rand = new Random();

    boolean gameOver = false;

    int solution;
    int operand1;
    int operand2;

    int correctGuesses;
    int totalGuesses;

    int currentSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pickOperands();

    }

    public void pickOperands() {
        operand1 = rand.nextInt(21) + 1;
        operand2 = rand.nextInt(31) + 1;

        solution = operand1 + operand2;
    }


    public void startGame(View view) {

        correctGuesses = 0;
        totalGuesses = 0;

        Button goBtn = findViewById(R.id.goButton);
        if(goBtn.getVisibility() != View.GONE) {
            goBtn.setVisibility(View.GONE);
        }

        TextView countDownView = findViewById(R.id.countdownView);
        if(countDownView.getVisibility() != View.VISIBLE) {
            countDownView.setVisibility(View.VISIBLE);
        }

        TextView scoreView = findViewById(R.id.scoreView);
        if(scoreView.getVisibility() != View.VISIBLE) {
            scoreView.setVisibility(View.VISIBLE);
        }
        updateScoreDisplay();
        setProblem();
        setBoard();

        GridLayout choiceView = (GridLayout) findViewById(R.id.gridLayout);
        if(choiceView.getVisibility() != View.VISIBLE) {
            choiceView.setVisibility(View.VISIBLE);
        }

        setTimer(31);
        timer.start();
    }

    public void setProblem() {
        TextView problemView = findViewById(R.id.problemView);
        problemView.setText(operand1 + " + " + operand2);
        if(problemView.getVisibility() != View.VISIBLE) {
            problemView.setVisibility(View.VISIBLE);
        }
    }

    public void setBoard(){
        int winningLoc = rand.nextInt(4);
        Set<Integer> solutions = new HashSet<>();
        solutions.add(solution);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); ++i) {

            TextView child = (TextView) gridLayout.getChildAt(i);

            if(i == winningLoc) {
                child.setText(String.valueOf(solution));
            } else {
                int temp = rand.nextInt(operand1 + operand2 * 2 ) + 1;
                if(solutions.contains(temp)) {
                    temp++;
                }
                solutions.add(temp);
                child.setText(String.valueOf(temp));
            }

//            ImageView child = (ImageView) gridLayout.getChildAt(i);
//            //child.setImageResource(0);
//            child.setImageDrawable(null);
        }

    }

    public void checkGuess(View view) {

        if(!gameOver) {
            TextView guess = (TextView) view;
            Log.i("guess", (String) guess.getText());
            TextView result = (TextView) findViewById(R.id.resultView);

            if (Integer.parseInt((String) guess.getText()) == solution) {
                result.setText("Correct!");
                ++correctGuesses;

                timer.cancel();
                setTimer(currentSeconds + 4);
                timer.start();

            } else {
                timer.cancel();
                setTimer(currentSeconds - 3);
                timer.start();
                result.setText("Wrong :(");
            }
            pickOperands();
            setBoard();
            setProblem();
            ++totalGuesses;

            updateScoreDisplay();

            if (result.getVisibility() != View.VISIBLE) {
                result.setVisibility(View.VISIBLE);
            }
        }else {
            Toast.makeText(this, "Please Play Again", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateScoreDisplay() {
        TextView scoreDisplay = (TextView) findViewById(R.id.scoreView);
        scoreDisplay.setText(correctGuesses + "/" + totalGuesses);
    }


    public void setTimer(final int seconds) {
        timer = new CountDownTimer(seconds * 1000, 1000) {

            int secs = seconds;
            int time = secs;
            @Override
            public void onTick(long millisecondsUntilFinished) {
                updateDisplaytimer(millisecondsUntilFinished);
                currentSeconds = time--;
                Log.i("timer", String.valueOf(time));
            }

            @Override
            public void onFinish() {
                gameOver = true;
                TextView result = findViewById(R.id.resultView);

                if (result.getVisibility() != View.VISIBLE){
                    result.setVisibility(View.VISIBLE);
                }


                Button playAgainBtn = (Button) findViewById(R.id.playAgainButton);
                playAgainBtn.setVisibility(View.VISIBLE);
                playAgainBtn.setClickable(true);
                updateDisplaytimer(0);
                result.setText("Done!");
            }
        };
    }

    public void updateDisplaytimer(long millisecondsUntilFinished) {

        int secondsRemaining = (int) millisecondsUntilFinished / 1000;

        TextView countdownDisplay = findViewById(R.id.countdownView);
        countdownDisplay.setText(String.valueOf(secondsRemaining) + "s");
    }

    public void resetGame(View view) {
        Button playAgain = (Button) view;
        gameOver = false;
        startGame(view);

        TextView result = (TextView) findViewById(R.id.resultView);
        result.setVisibility(View.GONE);

        playAgain.setVisibility(View.GONE);
    }


}