package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultAnalysis extends AppCompatActivity {

    private HashMap<Integer, List<String>> choices = new HashMap<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_analysis);


        Intent login = getIntent();
        choices = (HashMap)login.getExtras().getSerializable("Choices");
        user = getIntent().getExtras().getParcelable("user");

        ((TextView)findViewById(R.id.tv_ap_wm)).setText(String.format("Score Analysis for %s", user.getFirstName()));

        TextView result = findViewById(R.id.tv_ra_result);

        result.setText(getDesc());

    }

    private String getDesc(){
        StringBuffer resultBuffer = new StringBuffer();

        resultBuffer.append(user.getFirstName()).append(", you have made choices as below: \n\n");
        int count = 1;
        String lastAnswer = "no";
        for(List<String> choice : choices.values()){
            resultBuffer.append("\n");
            resultBuffer.append(String.format(" Q. %s : ", count));
            resultBuffer.append(choice);
            lastAnswer = choice.toString();
            count++;
        }

        resultBuffer.append("\n\n");

        if(lastAnswer.contains("Yes")){
            resultBuffer.append("We are happy to learn that our model has predicted correctly!\n\n");
            user.setYesCount(user.getYesCount() + 1);
        } else {
            resultBuffer.append("Our model is still learning. Keep using our game to train our models.\n\n");
            user.setNoCount(user.getNoCount() + 1);
        }

        resultBuffer.append(String.format("Your Analysis\n\nYes Count : %s\nNo Count : %s", user.getYesCount(), user.getNoCount()));

        return resultBuffer.toString();
    }

    public void gotolandingpage(View view) {
        Intent loginCredIntent = new Intent(this, LandingPage.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.LANDING_PAGE);
    }
}
