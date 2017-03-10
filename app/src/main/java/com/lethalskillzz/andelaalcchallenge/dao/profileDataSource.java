package com.lethalskillzz.andelaalcchallenge.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper;
import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;

import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.COLUMN_FOLLOWERS;
import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.COLUMN_FOLLOWING;
import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.COLUMN_LOGIN;
import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.COLUMN_NAME;
import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.COLUMN_REPOS;
import static com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper.TABLE_PROFILE;

/**
 * Created by lethalskillzz on 09/03/2017.
 */

public class ProfileDataSource {


    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { COLUMN_LOGIN, COLUMN_NAME, COLUMN_FOLLOWING, COLUMN_FOLLOWERS, COLUMN_REPOS };

    public ProfileDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        dbHelper.close();
    }


    /**
     * Creating Profiles
     */
    public boolean createProfile(ProfileItem profileItem) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, profileItem.getLogin());
        values.put(COLUMN_NAME, profileItem.getName());
        values.put(COLUMN_FOLLOWING, profileItem.getFollowing());
        values.put(COLUMN_FOLLOWERS, profileItem.getFollowers());
        values.put(COLUMN_REPOS, profileItem.getPublicRepos());

        // insert row
        database.insert(DatabaseHelper.TABLE_PROFILE, DatabaseHelper.COLUMN_ID, values);

        return true;
    }


    /**
     * Read Single Profile by Login
     */
    public ProfileItem readProfileByLogin(String login) {

        String whereClause = COLUMN_LOGIN + " LIKE ?";
        String [] whereArgs = {"%"+login+"%"};
        ProfileItem profileItem = new ProfileItem();

        Cursor cursor = database.query(TABLE_PROFILE,
                allColumns,whereClause, whereArgs, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            profileItem = cursorToProfile(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return profileItem;
    }



    private ProfileItem cursorToProfile(Cursor cursor) {
        ProfileItem item = new ProfileItem();
        item.setLogin(cursor.getString(0));
        item.setName(cursor.getString(1));
        item.setFollowing(cursor.getString(2));
        item.setFollowers(cursor.getString(3));
        item.setPublicRepos(cursor.getString(4));

        return item;
    }


    /**
     * Delete Single Profile
     */
    public void deleteProfileItem(ProfileItem profileItem) {
        String id = profileItem.getLogin();
        database.delete(DatabaseHelper.TABLE_PROFILE, COLUMN_LOGIN
                + " = " + "\"" + id + "\"", null);
    }

    /**
     * Clear All Profile
     */
    public void clearAllProfile() {
        database.delete(DatabaseHelper.TABLE_PROFILE, null, null);
    }


}
