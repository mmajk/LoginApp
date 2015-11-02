package com.example.jakob.loginapp.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jakob.loginapp.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Created by jakob on 12-10-2015.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String TAG = "DBHelper Log: ";
    private static final String DATABASE_NAME = "LoginAppDb";
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE_NAME = "users";
    private static final String USERS_COLUMN_ID = "tbl_id";
    private static final String USERS_COLUMN_USERNAME = "tbl_username";
    private static final String USERS_COLUMN_EMAIL = "tbl_email";
    private static final String USERS_COLUMN_PASSWORD = "tbl_password";
    private static final String USER_COLUMN_CREATED_AT   = "tbl_created_at";
    private static final String USER_COLUMN_IS_LOGGED_IN = "tbl_isLoggedIn";



    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE USERS " + "("
                + USERS_COLUMN_ID + " integer primary key autoincrement,"
                + USERS_COLUMN_USERNAME + " text,"
                + USERS_COLUMN_EMAIL + " text not null,"
                + USERS_COLUMN_PASSWORD + " text not null,"
                + USER_COLUMN_IS_LOGGED_IN + " integer default 0,"
                + USER_COLUMN_CREATED_AT + " text" + ")";
        db.execSQL(createUsersTable);
        Log.v(TAG,createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password){
        Date d = new Date();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tbl_username", username);
        contentValues.put("tbl_email", email);
        contentValues.put("tbl_password", password);
        contentValues.put("tbl_isLoggedIn",0);
        contentValues.put("tbl_created_at", d.toString());
        db.insert("users", null, contentValues);
        return true;
    }

    public boolean updateUser(int id, String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tbl_username", username);
        contentValues.put("tbl_email", email);
        contentValues.put("tbl_password", password);
        db.update("users", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllUsers()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_USERNAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public Boolean seIfUserExistsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + USERS_TABLE_NAME + " where tbl_email = '" + email +"'" , null);

        Boolean isThere = res.moveToFirst();
        return isThere;
    }

    public User findRegistredUser(String email, String password) throws SQLiteException{
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "select * from " + USERS_TABLE_NAME + " where tbl_email = '" + email +"'";
        Cursor res = db.rawQuery(query, null);
        if (res == null ){
            Log.v(TAG, "res null");
            return user;
        }else {
            try {

                if (res.moveToFirst()) {
                    do {
                        user = new User();
                        user.setId(res.getInt(res.getColumnIndexOrThrow(USERS_COLUMN_ID)));
                        user.setUserName(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_USERNAME)));
                        user.setEmail(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_EMAIL)));
                        user.setPassword(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_PASSWORD)));
                    }while (res.moveToNext());
                }
            }catch (SQLiteException e) {
                Log.v(TAG, e.getMessage());
            }finally {
                res.close();
                db.close();
            }
        }

        return user;
    }

    public void setUserLoggedIn(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "update " + USERS_TABLE_NAME + "set " + USER_COLUMN_IS_LOGGED_IN + " = '1' where " + USERS_COLUMN_ID + " = '" + user.getId() +"'";
        if(user == null )
            throw new IllegalArgumentException("user");
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_IS_LOGGED_IN, 1);

        db.update(USERS_TABLE_NAME, contentValues, "tbl_id = " + user.getId(),null);
    }

    public void setUserLoggedOut(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_IS_LOGGED_IN, 0);
        db.update(USERS_TABLE_NAME, contentValues, "tbl_id=" + user.getId(), null);
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
       // String query = "select from " + USERS_TABLE_NAME + "where tbl_id = '" + id + "'";
        String query = "select * from " + USERS_TABLE_NAME + " where tbl_id = '" + id +"'";
        Cursor res = db.rawQuery(query, null);
        if (res == null ){
            Log.v(TAG, "res null");
            return user;
        }else {
            try {

                if (res.moveToFirst()) {
                    do {
                        user = new User();
                        user.setId(res.getInt(res.getColumnIndexOrThrow(USERS_COLUMN_ID)));
                        user.setUserName(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_USERNAME)));
                        user.setEmail(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_EMAIL)));
                        user.setPassword(res.getString(res.getColumnIndexOrThrow(USERS_COLUMN_PASSWORD)));
                    }while (res.moveToNext());
                }
            }catch (SQLiteException e) {
                Log.v(TAG, e.getMessage());
            }finally {
                res.close();
                db.close();
            }
        }

        return user;

    }

    /**
     * Re create db
     */
    public void resetTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(USERS_TABLE_NAME, null, null);
        db.close();
    }
}
