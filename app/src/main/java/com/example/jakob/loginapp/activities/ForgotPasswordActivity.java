package com.example.jakob.loginapp.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakob.loginapp.R;
import com.example.jakob.loginapp.dal.DBHelper;
import com.example.jakob.loginapp.helpers.EmailValidator;

/**
 * Created by jakob on 15-10-2015.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private Button resetPasswordButton;
    private TextView restEmailTextView;
    private DBHelper myDBHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);
        myDBHelper = new DBHelper(this);

        restEmailTextView = (TextView) findViewById(R.id.editTextRecoveryEmail);
        resetPasswordButton = (Button) findViewById(R.id.btnRecoverPassword);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEmail();
                Toast toast = Toast.makeText(ForgotPasswordActivity.this, "An email was send", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                if(verifyEmail())
                toast.show();

            }
        });
    }

    public void sendMail(){

        try{
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {restEmailTextView.getText().toString()});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Password recover");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "hello from android");
            emailIntent.setType("message/rfc822");

            startActivity(emailIntent);

        }catch(ActivityNotFoundException e){
            Toast.makeText(ForgotPasswordActivity.this, "No email client found: ", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean verifyEmail(){
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.validate(restEmailTextView.getText().toString())){
            Toast toast = Toast.makeText(ForgotPasswordActivity.this, "You have to provide a valid email", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            return false;
        }else if(!myDBHelper.seIfUserExistsByEmail(restEmailTextView.getText().toString())) {
            Toast toast = Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
            return false;
        }else
            sendMail();
        return true;
    }

    public void mailAuth(){

    }




}
