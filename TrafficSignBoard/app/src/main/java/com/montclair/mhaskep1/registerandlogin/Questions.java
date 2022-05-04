package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.montclair.mhaskep1.registerandlogin.model.QuestionAnswer;
import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.service.QuestionService;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity to keep track of questions and track users choices
 *
 */
public class Questions extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener {

    private Bundle outState;
    private QuestionAnswer questionAnswer;
    private Map<Integer, List<String>> choices = new HashMap<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        user = getIntent().getExtras().getParcelable("user");

        if(savedInstanceState == null) {
            questionAnswer = QuestionService.findQuestionBySeq(1, choices);
        } else {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.frg_question);

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();

            questionAnswer = savedInstanceState.getParcelable(Constants.parcel_question);
        }
        showFragment(questionAnswer);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.outState = outState;
        this.outState.putParcelable(Constants.parcel_question, questionAnswer);
    }

    private void gotoAnalysisActivity() {
        Intent analysisIntent = new Intent(this, ResultAnalysis.class);
        analysisIntent.putExtra("loginMsg", "Login User");
        analysisIntent.putExtra("Choices", (HashMap)choices);
        analysisIntent.putExtra("user", user);
        startActivityForResult(analysisIntent, Constants.RESULTANALYSIS_PAGE);
    }

    private void gotoHomeActivity() {
        Intent loginCredIntent = new Intent(this, LandingPage.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        startActivityForResult(loginCredIntent, Constants.RESULTANALYSIS_PAGE);
    }

    @Override
    public void onFragmentInteraction(QuestionAnswer questionAnswer) {

    }


    @Override
    public void onPrev(QuestionAnswer questionAnswer) {

        if(!questionAnswer.getChoices().isEmpty()){

            int current = questionAnswer.getQuestionNumber();
            choices.put(current, questionAnswer.getChoices());

            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentByTag(String.valueOf(current));

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();

            if(current > 1) {

                QuestionAnswer nextQuestionAnswer = QuestionService.findQuestionBySeq(current - 1, choices);

                this.questionAnswer = nextQuestionAnswer;
                showFragment(nextQuestionAnswer);

            } else {
                gotoHomeActivity();
            }
        }
    }


    @Override
    public void onNext(QuestionAnswer questionAnswer) {

        if(!questionAnswer.getChoices().isEmpty()){

            int current = questionAnswer.getQuestionNumber();
            choices.put(current, questionAnswer.getChoices());

            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentByTag(String.valueOf(current));

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();

            if(current < 5) {

                QuestionAnswer nextQuestionAnswer = QuestionService.findQuestionBySeq(current + 1, choices);

                this.questionAnswer = nextQuestionAnswer;
                showFragment(nextQuestionAnswer);

            } else {
                gotoAnalysisActivity();
            }
        }
    }

    public void showFragment(QuestionAnswer questionAnswer) {

        QuestionFragment questionFragment = QuestionFragment.newInstance(questionAnswer);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frg_question, questionFragment, String.valueOf(questionAnswer.getQuestionNumber()))
                .commit();
    }
}
