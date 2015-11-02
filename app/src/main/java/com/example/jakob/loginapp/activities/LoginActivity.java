package com.example.jakob.loginapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jakob.loginapp.R;
import com.example.jakob.loginapp.dal.DBHelper;
import com.example.jakob.loginapp.models.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login Activity: ";
    private EditText email;
    private EditText password;
    private Button signIn;
    private Button register;
    private Button forgotPassword;
    private DBHelper myDBHelper;
    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        myDBHelper = new DBHelper(this);

        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        signIn = (Button) findViewById(R.id.btnSignIn);
        register = (Button) findViewById(R.id.btnRegister);
        forgotPassword = (Button) findViewById(R.id.btnForgotPassword);

        signIn.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                SignIn();
                break;
            case R.id.btnRegister:
                Intent signInIntent = new Intent(v.getContext(), RegisterActivity.class);
                startActivityForResult(signInIntent, 0);
                finish();
                break;
            case R.id.btnForgotPassword:
                Intent resetPassword = new Intent(v.getContext(), ForgotPasswordActivity.class);
                startActivityForResult(resetPassword, 0);
                finish();
                break;

        }
    }

    public void SignIn(){
        String mail = email.getText().toString();
        String pasw = password.getText().toString();
        User user = myDBHelper.findRegistredUser(mail, pasw);
        if(user == null) {
            Toast toast = Toast.makeText(LoginActivity.this, "" +
                    "Please regiter before login!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            return;
        }
        Log.v(TAG, user.getUserName() + " " + user.getPassword() + " " +user.getId());
        if (!user.getEmail().equals(mail) || !user.getPassword().equals(pasw)){
            Toast toast = Toast.makeText(this, "Wrong password or email", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            return;
        }
        Log.v(TAG, Integer.toString(user.getId()));
        myDBHelper.setUserLoggedIn(user);
        userId = user.getId();


        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivityForResult(mainActivity, 0);
    }


}
