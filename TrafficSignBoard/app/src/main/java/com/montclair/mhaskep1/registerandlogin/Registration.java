package com.montclair.mhaskep1.registerandlogin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.montclair.mhaskep1.registerandlogin.model.User;
import com.montclair.mhaskep1.registerandlogin.service.LoginService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Registrer user Activity
 */
public class Registration extends AppCompatActivity {

    boolean validForm = false;
    boolean validDate = false;
    EditText firstnamevalue;
    EditText lastnamevalue;
    EditText email;
    EditText passwordvalue;
    DatePicker datePicker;
    Date selectedDate;
    TextView dateTextView;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done_white_24dp);


        Intent login = getIntent();
        String loginMsg = login.getExtras().getString("loginMsg");

        Toast.makeText(this, String.format("Registration received Login Msg :: %s", loginMsg), Toast.LENGTH_LONG).show();



        firstnamevalue = findViewById(R.id.firstnamevalue);
        lastnamevalue = findViewById(R.id.lastnamevalue);
        email = findViewById(R.id.emailvalue);
        passwordvalue = findViewById(R.id.passwordvalue);
        datePicker = findViewById(R.id.datepicker);
        dateTextView = findViewById(R.id.dob);

        firstnamevalue.addTextChangedListener(regTextWatcher);
        lastnamevalue.addTextChangedListener(regTextWatcher);
        email.addTextChangedListener(regTextWatcher);
        passwordvalue.addTextChangedListener(regTextWatcher);

        datePicker.setMaxDate(System.currentTimeMillis());

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                selectedDate = calendar.getTime();

                dateTextView.setText(String.format("Date of Birth - %s ",
                        android.text.format.DateFormat.format("MM/dd/yyyy", selectedDate)));

                validDate = true;

            }
        });
    }


    /**
     * Validate user and send user to regitration to LoginService layer
     *
     * @param view
     */
    public void registerUser(View view) {
        validForm = true;

        //TODO : validate user registration
        validateEmptyField(firstnamevalue);
        validateEmptyField(lastnamevalue);
        validateEmptyField(email);
        validateEmptyField(passwordvalue);
        TextView result = findViewById(R.id.result);
        isValidEmail(email);
        validatepassword(passwordvalue);
        validateDate();


        //TODO : return result
        if (!validForm) {
            result.setText("Please enter value in RED field(s) above");
            Toast.makeText(this, "Please enter value in RED fields ", Toast.LENGTH_LONG).show();
            return;
        } else {

            //save user
            User user = LoginService.addUser(firstnamevalue.getText().toString(),
                    lastnamevalue.getText().toString(),
                    selectedDate,
                    email.getText().toString(),
                    passwordvalue.getText().toString()
                    );
        }
        onBackPressed();
    }

    private void validateDate() {
        if(!validDate){
            ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        } else {
            ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setBackground(new ColorDrawable(getResources().getColor(R.color.colorWhite)));
        }
    }

    /**
     * Initialize intent based on validation
     */
    @Override
    public void onBackPressed() {
        Log.d("please", "onBackPressed: ");
        Intent loginIntent = new Intent();
        loginIntent.putExtra("validForm", validForm);

        if (validForm) {
            setResult(RESULT_OK, loginIntent);
        } else {
            setResult(RESULT_CANCELED, loginIntent);
        }

        super.onBackPressed();
        finish();
    }
//validate email field
    public void isValidEmail(EditText email)
    {
        if(email == null){
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setBackgroundColor(Color.WHITE);
        } else {
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            email.setError(getString(R.string.valid_email_format), errorImage);
            validForm = false;
        }
    }
//validate name fields
    private void validateName(EditText e) {
        if (e.getText().toString().trim().isEmpty()) {
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            e.setError(getString(R.string.mandatory_field), errorImage);
            validForm = false;
        } else if (e.getText().toString().trim().length() < 3) {
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            e.setError(getString(R.string.mandatory_field), errorImage);
            validForm = false;
        } else {
            e.setBackgroundColor(Color.WHITE);
        }
    }
// Validate empty fields
    private void validateEmptyField(EditText e) {
        if (e.getText().toString().trim().isEmpty()) {
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            e.setError(getString(R.string.mandatory_field), errorImage);

            validForm = false;
        } else {
            e.setBackgroundColor(Color.WHITE);
        }
    }
//using datepicker to set date
    public void read_date(View view) {
        if(View.GONE == datePicker.getVisibility()){
            datePicker.setVisibility(View.VISIBLE);
            datePicker.requestFocus();
        } else {
            datePicker.setVisibility(View.GONE);
        }
    }

    //to do validate password field
    private void validatepassword(EditText e)
    {
        if (e.getText().toString().trim().isEmpty()) {
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            e.setError(getString(R.string.enter_password), errorImage);

            validForm = false;
        } else if (e.getText().toString().trim().length() < 6 )
         {
            //e.setBackgroundColor(Color.RED);
            Drawable errorImage = (Drawable)getResources().getDrawable(R.drawable.ic_error_red_24dp);
            errorImage.setBounds(0, 0, errorImage.getIntrinsicWidth(), errorImage.getIntrinsicHeight());
            final String error = getString(R.string.error_6_char_length);
            e.setError(error, errorImage);

            validForm = false;
        } else {
            e.setBackgroundColor(Color.WHITE);
        }
    }

    private TextWatcher regTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            return;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            validForm = true;
            validateEmptyField(firstnamevalue);
            validateEmptyField(lastnamevalue);
            validateEmptyField(email);
            validateEmptyField(passwordvalue);
            //validateEmptyField(datePicker);
            validateName(firstnamevalue);
            validateName(lastnamevalue);
            isValidEmail(email);

            validatepassword(passwordvalue);

            return;
        }
    };

}