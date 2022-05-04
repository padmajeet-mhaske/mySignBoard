package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

public class LandingPage extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        user = getIntent().getExtras().getParcelable("user");

        //((TextView)findViewById(R.id.tv_lp_wm)).setText(String.format("Welcome, %s", user.getFirstName()));

    }

    public void startQuiz(View view) {

        Intent loginCredIntent = new Intent(this, Questions.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.QUESTIONS_PAGE);

    }

    public void gotoBuy(View view) {

        /*Intent loginCredIntent = new Intent(this, buy.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.QUESTIONS_PAGE);*/

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.montclair.mhaskep1.gtsrb");
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(LandingPage.this, "There is no package available in android", Toast.LENGTH_LONG).show();
        }


    }

    public void gotoSell(View view) {

        Intent loginCredIntent = new Intent(this, sell.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.QUESTIONS_PAGE);

    }

    public void gotoRent(View view) {

        Intent loginCredIntent = new Intent(this, rent.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.QUESTIONS_PAGE);

    }
}
