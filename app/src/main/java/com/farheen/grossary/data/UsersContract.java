package com.farheen.grossary.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Farheen on 16/02/2017.
 */

public class UsersContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UsersContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.farheen.grossary";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.qurizm.blackhole/blackhole_users/ is a valid path for
     * looking at pet data. content://com.qurizm.blackhole/blackhole_users/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_GAME_USERS = "grossary_users";
//    public static final String PATH_USERS_SCORES = "blackhole_users_scores";
    /**
     * Inner class that defines constant values for the Game database table.
     * Each entry in the table represents a single user.
     */

    public static final class MaterialUserEntry implements BaseColumns {

        /** The content URI to access the user data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GAME_USERS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of users.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME_USERS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single user.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME_USERS;

        /** Name of database table for users */
        public final static String TABLE_NAME = "grossary_users";

        /**
         * Unique ID number for the user (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * UserName of the user.
         *
         * Type: TEXT
         */
        public final static String COLUMN_USERNAME ="username";

        /**
         * password of the user.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PASSWORD="password";
    }

    /*public static final class UserScoreEntry implements BaseColumns {

        *//** The content URI to access the user data in the provider *//*
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS_SCORES);

        *//**
         * The MIME type of the {@link #CONTENT_URI} for a list of scores.
         *//*
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS_SCORES;

        *//**
         * The MIME type of the {@link #CONTENT_URI} for a single score.
         *//*
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME_USERS;

        *//** Name of database table for users *//*
        public final static String TABLE_NAME = "user_score";

        *//**
         * Unique ID number for the user (only for use in the database table).
         *
         * Type: INTEGER
         *//*
        public final static String _ID = BaseColumns._ID;

        *//**
         * UserName of the first player (red).
         *
         * Type: TEXT
         *//*
        public final static String COLUMN_PLAYER1 ="player1";

        *//**
         * UserName of the second player (blue)..
         *
         * Type: TEXT
         *//*
        public final static String COLUMN_PLAYER2="player2";

        *//**
         * Number of wins by player1 (red)
         *
         * Type: Int
         *//*
        public final static String COLUMN_PLAYER1_WINS ="player1_wins";

        *//**
         * Number of wins by player2 (blue)
         *
         * Type: Int
         *//*
        public final static String COLUMN_PLAYER2_WINS="player2_wins";


        *//**
         * Number of Draw between these 2 players
         *
         * Type: Int
         *//*
        public final static String COLUMN_DRAWS ="draws";

    }*/

}
