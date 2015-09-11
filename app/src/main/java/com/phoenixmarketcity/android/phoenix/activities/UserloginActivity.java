package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserloginActivity extends BaseActivity {
    //declaration of variable//
    private EditText emailEditText;
    private EditText passEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_usersignin);
        final LinearLayout frgtpwd =(LinearLayout)findViewById(R.id.frgtpwd);
        final LinearLayout signinlayout =(LinearLayout)findViewById(R.id.signinlayout);
        final TextView txtfgtpwd =(TextView)findViewById(R.id.txtfrgtpwd);
        final LinearLayout social =(LinearLayout)findViewById(R.id.sociallayouts);
       //onclick forgot password the layout's visibility is set to gone and forgot passwword layout is set to visible//
        txtfgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgtpwd.setVisibility(View.VISIBLE);
                signinlayout.setVisibility(View.GONE);
                social.setVisibility(View.GONE);
            }
        });



        //declarations of objects of variables to find id//

        emailEditText = (EditText) findViewById(R.id.emailtext);
        passEditText = (EditText) findViewById(R.id.passwordtext);
        TextView signUp = (TextView)findViewById(R.id.userSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserloginActivity.this,UserRegistrationActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.signinbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final String email = emailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email");
                }

                final String pass = passEditText.getText().toString();
                if (!isValidPassword(pass)) {
                    passEditText.setError("Invalid Password");
                } else {
                    Intent intent = new Intent(UserloginActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                }
            }


        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 7) {
            return true;
        }

        return false;



    }

}



