package com.example.jakob.loginapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import com.example.jakob.loginapp.R;
import com.example.jakob.loginapp.dal.DBHelper;
import com.example.jakob.loginapp.helpers.EmailValidator;
import com.example.jakob.loginapp.helpers.RegisterValidator;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Register Activity: ";
    EditText userName;
    EditText email;
    EditText password;
    EditText repeatPassword;
    Button register;
    private DBHelper myDbHelper;
    private EmailValidator emailValidator;
    private RegisterValidator registerValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userName = (EditText) findViewById(R.id.editTextUsername);
        email = (EditText) findViewById(R.id.editTextRegisterEmail);
        password = (EditText) findViewById(R.id.editTextRegisterPassword);
        repeatPassword = (EditText) findViewById(R.id.editTextRegisterPasswordRepeat);
        register = (Button) findViewById(R.id.btnRegisterRegister);
        register.setOnClickListener(this);

        myDbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRegisterRegister:
                registerUser();
                break;
        }
    }

    public void registerUser(){
        emailValidator = new EmailValidator();
        registerValidator = new RegisterValidator();
        String name = userName.getText().toString();
        String mail = email.getText().toString();
        String pasw = password.getText().toString();
        String repPass = repeatPassword.getText().toString();

        if(name.trim().equals("") || mail.trim().equals("") || pasw.equals("") || repPass.equals("")){
            Toast toast = Toast.makeText(RegisterActivity.this, "No fields can't be emty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            return;
        }else if(!registerValidator.vallidateUserName(name)){
            Toast.makeText(RegisterActivity.this, "User name has to be at least 2 characters", Toast.LENGTH_SHORT).show();
        }
        if (myDbHelper.seIfUserExistsByEmail(mail)){
            Toast toast = Toast.makeText(this, "This email is allready registered", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            email.setText("");
            return;

        }else if(!emailValidator.validate(mail)){
            Toast toast = Toast.makeText(RegisterActivity.this, "You have to provide a valid email", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            email.setText("");
            return;
        }else if(!pasw.equals(repPass)){
            Toast toast = Toast.makeText(this, "The passwords don't match", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            password.setText("");
            repeatPassword.setText("");
            return;
        }
            try {
                myDbHelper.insertUser(name, mail, pasw);
            }catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Log.v(TAG, name);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, 0);


    }

    public void clearFields(){
        userName.setText("");
        email.setText("");
        password.setText("");
        repeatPassword.setText("");
    }
}
