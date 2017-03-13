package com.lethalskillzz.andelaalcchallenge.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;

import java.util.ArrayList;
import java.util.List;

import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.*;

/**
 * Created by lethalskillzz on 05/03/2017.
 */

public class UserDataSource {


    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { COLUMN_ID, COLUMN_LOGIN, COLUMN_AVATAR_URL, COLUMN_URL };

    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        dbHelper.close();
    }


    /**
     * Check if User are empty
     */
    public boolean isUserEmpty() {

        boolean isEmpty = true;
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            isEmpty = false;
        }
        cursor.close();

        return isEmpty;
    }



    /**
     * Creating Users
     */
    public UserItem createUser(UserItem userItem) {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, userItem.getId());
            values.put(DatabaseHelper.COLUMN_LOGIN, userItem.getLogin());
            values.put(DatabaseHelper.COLUMN_AVATAR_URL, userItem.getAvatarUrl());
            values.put(DatabaseHelper.COLUMN_URL, userItem.getUrl());

            // insert row
            database.insert(DatabaseHelper.TABLE_USER, DatabaseHelper.COLUMN_ID, values);


        return userItem;
    }


    /**
     * Read All Users
     */
    public List<UserItem> readAllUser() {

        String whereClause = null;
        String[] whereArgs = null;
        List<UserItem> userItems = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns, whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserItem item = cursorToUser(cursor);
            userItems.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userItems;
    }


    private UserItem cursorToUser(Cursor cursor) {
        UserItem item = new UserItem();
        item.setId(cursor.getString(0));
        item.setLogin(cursor.getString(1));
        item.setAvatarUrl(cursor.getString(2));
        item.setUrl(cursor.getString(3));

        return item;
    }


    /**
     * Read User by Login
     */
    public List<UserItem> readUserByLogin(String login) {

        String whereClause = COLUMN_LOGIN + " LIKE ?";
        String [] whereArgs = {"%"+login+"%"};
        List<UserItem> userItems = new ArrayList<>();

        Cursor cursor = database.query(TABLE_USER,
                allColumns,whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserItem userItem = cursorToUser(cursor);
            userItems.add(userItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userItems;
    }


//    public void updatePermanentTin(EnumItem enumItem){
//
//        ContentValues newValues = new ContentValues();
//        newValues.put(COLUMN_PERMANENT_TIN, enumItem.getPermanentTin());
//        newValues.put(COLUMN_IS_UPDATE, enumItem.getIsUpdate());
//
//        String[] args = new String[]{enumItem.getEnumCode()};
//        database.update(TABLE_ENUM, newValues, COLUMN_ENUM_CODE+"=?", args);
//    }

    /**
     * Delete Single User
     */
    public long deleteUserItem(UserItem userItem) {
        String id = userItem.getId();
        return database.delete(DatabaseHelper.TABLE_USER, COLUMN_ID
                + " = " + id, null);
    }

    /**
     * Clear All User
     */
    public void clearAllUser() {
        database.delete(DatabaseHelper.TABLE_USER, null, null);
    }


}
