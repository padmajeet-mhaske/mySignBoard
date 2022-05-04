package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

public class sell extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);


        user = getIntent().getExtras().getParcelable("user");


    }


    public void gotoBuy(View view) {

        Intent loginCredIntent = new Intent(this, buy.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        loginCredIntent.putExtra("user", user);
        startActivityForResult(loginCredIntent, Constants.QUESTIONS_PAGE);

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
