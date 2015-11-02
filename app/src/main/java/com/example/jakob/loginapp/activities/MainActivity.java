package com.example.jakob.loginapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jakob.loginapp.R;
import com.example.jakob.loginapp.dal.DBHelper;
import com.example.jakob.loginapp.models.User;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivivty log: ";
    private Button buttonLogout;
    private TextView textViewUserName;
    private TextView textViewEmail;
    private User user;
    private DBHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDbHelper = new DBHelper(this);
        user = myDbHelper.getUserById(LoginActivity.userId);
        textViewUserName = (TextView) findViewById(R.id.textViewUserNameLoginScreen);
        textViewEmail = (TextView) findViewById(R.id.textViewEmailloginScreen);
        buttonLogout = (Button) findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbHelper.setUserLoggedOut(user);
                Intent login = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(login, 0);
                finish();
                Log.v(TAG, "" + user.getUserName());
            }
        });

        setInfoOnScreen();


    }

    public boolean isUserLoggedIn(){
        if(user.isLoggenIn() == 1){
            return true;
        }
            return false;
    }

    public void setInfoOnScreen(){
        if(!isUserLoggedIn()){
            textViewUserName.setText(this.user.getUserName());
            textViewEmail.setText(this.user.getEmail());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
