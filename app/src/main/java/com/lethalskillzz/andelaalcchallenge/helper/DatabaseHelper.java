package com.lethalskillzz.andelaalcchallenge.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lethalskillzz on 8/17/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    //TABLE
    public static final String TABLE_USER = "user";

    //COLUMN
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_AVATAR_URL = "avatar_url";
    public static final String COLUMN_URL = "url";

    private static final String DATABASE_NAME = "andela_alc.db";
    private static final int DATABASE_VERSION = 1;


    //Database creation sql statement
    private static final String DATABASE_CREATE_USER = "CREATE TABLE " + TABLE_USER + "( _id INTEGER PRIMARY KEY"
            + COLUMN_ID + " TEXT NOT NULL, " + COLUMN_LOGIN + " TEXT NOT NULL, " + COLUMN_AVATAR_URL +
            " TEXT NOT NULL , " + COLUMN_URL + " TEXT NOT NULL );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
