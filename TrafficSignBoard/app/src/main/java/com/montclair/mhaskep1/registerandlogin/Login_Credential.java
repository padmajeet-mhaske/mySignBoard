package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.service.LoginService;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

public class Login_Credential extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__credential);
    }

    /**
     * Validate login credentials. Based on it, user will go to landing page or show error message.
     *
     * @param view
     */
    public void loginUser(View view) {

        String email = ((EditText)findViewById(R.id.et_login_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_login_password)).getText().toString();

        User user = LoginService.findUserByLogin(email, password);

        if(user != null){

            Intent loginCredIntent = new Intent(this, LandingPage.class);
            loginCredIntent.putExtra("loginMsg", "Login User");
            loginCredIntent.putExtra("user", user);
            startActivityForResult(loginCredIntent, Constants.LANDING_PAGE);

        } else {
            TextView errMsg = findViewById(R.id.tv_lc_errmsg);
            errMsg.setText("Wrong Crdentials; Try again or use forgot password");
        }

    }
}
