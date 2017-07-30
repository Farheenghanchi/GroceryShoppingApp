package com.farheen.grossary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Farheen on 16/02/2017.
 */

/**
 * Database helper for BlackHole app. Manages database creation and version management.
 */
public class UsersDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = UsersDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "grossary.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link UsersDataProvider}.
     *
     * @param context context of app
     */
    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the Users table
        String SQL_CREATE_USERS_TABLE =
                "CREATE TABLE " + UsersContract.MaterialUserEntry.TABLE_NAME + " ("
                + UsersContract.MaterialUserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersContract.MaterialUserEntry.COLUMN_USERNAME + " TEXT NOT NULL, "
                + UsersContract.MaterialUserEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";



        // Create a String that contains the SQL statement to create the Score table
        /*String SQL_CREATE_SCORE_TABLE =  "CREATE TABLE " + UsersContract.UserScoreEntry.TABLE_NAME + " ("
                + UsersContract.UserScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersContract.UserScoreEntry.COLUMN_PLAYER1 + " TEXT NOT NULL, "
                + UsersContract.UserScoreEntry.COLUMN_PLAYER2 + " TEXT NOT NULL, "
                + UsersContract.UserScoreEntry.COLUMN_PLAYER1_WINS + " INTEGER NOT NULL DEFAULT 0, "
                + UsersContract.UserScoreEntry.COLUMN_PLAYER2_WINS + " INTEGER NOT NULL DEFAULT 0, "
                + UsersContract.UserScoreEntry.COLUMN_DRAWS + " INTEGER NOT NULL DEFAULT 0);";*/


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);
        // Execute the SQL statement
//        db.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
