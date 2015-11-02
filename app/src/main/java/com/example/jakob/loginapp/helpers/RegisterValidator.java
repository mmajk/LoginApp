package com.example.jakob.loginapp.helpers;

/**
 * Created by jakob on 19-10-2015.
 */
public class RegisterValidator {

    public boolean vallidateUserName(String name){

        if(name.length() < 3){
            return false;
        }
        return true;
    }
}
