package com.montclair.mhaskep1.registerandlogin;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.montclair.mhaskep1.registerandlogin.service.LoginService;
import com.montclair.mhaskep1.registerandlogin.util.Constants;

/**
 * Login page with register button
 *
 */
public class Login extends AppCompatActivity {

    /**
     * Check if users are present based on it login enabled/disabled
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(LoginService.hasUsers()){
            findViewById(R.id.login).setEnabled(true);
        } else {
            findViewById(R.id.login).setEnabled(false);
        }
    }

    /**
     * GO to registration activity.
     *
     * @param view
     */
    public void gotoRegisteration(View view) {
        Intent registerIntent = new Intent(this, Registration.class);
        registerIntent.putExtra("loginMsg", "Register New Bayout User");
        startActivityForResult(registerIntent, Constants.REGISTER_USER);
    }

    /**
     * Returns registerd user flag to detect user registration created or cancelled
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //always disable login button
        findViewById(R.id.login).setEnabled(false);

        if(requestCode == Constants.REGISTER_USER && resultCode == RESULT_OK){

            //TODO : enable button with successful user registration message
            findViewById(R.id.login).setEnabled(true);
            Toast.makeText(this, "Successful Registration ", Toast.LENGTH_LONG).show();
        }
        if(requestCode == Constants.REGISTER_USER && resultCode == RESULT_CANCELED){

            //TODO : enable button with successful user registration message
            Toast.makeText(this, "Registration Cancelled", Toast.LENGTH_LONG).show();
            findViewById(R.id.login).setEnabled(false);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Goes to login credential page
     *
     * @param view
     */
    public void gotoLoginCred(View view) {
        Intent loginCredIntent = new Intent(this, Login_Credential.class);
        loginCredIntent.putExtra("loginMsg", "Login User");
        startActivityForResult(loginCredIntent, Constants.LOGIN_USER);
    }
}
