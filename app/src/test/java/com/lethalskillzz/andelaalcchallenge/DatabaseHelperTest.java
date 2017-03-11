package com.lethalskillzz.andelaalcchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

/**
 * Created by lethalskillzz on 11/03/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class DatabaseHelperTest {

    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase db;



    @Before
    public void setup(){
        context = RuntimeEnvironment.application;
        helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
    }

    @After
    public void cleanup(){
        db.close();
    }

    @Test
    public void testDBCreated(){
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        // Verify is the DB is opening correctly
        assertTrue("DB didn't open", db.isOpen());
    }

    @Test
    public void testUserTableCols() {
        Cursor c = db.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
        assertNotNull( c );

        String[] cols = c.getColumnNames();
        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_ID,
                cols, hasItemInArray(DatabaseHelper.COLUMN_ID));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_LOGIN,
                cols, hasItemInArray(DatabaseHelper.COLUMN_LOGIN));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_AVATAR_URL,
                cols, hasItemInArray(DatabaseHelper.COLUMN_AVATAR_URL));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_URL,
                cols, hasItemInArray(DatabaseHelper.COLUMN_URL));

        c.close();
    }


    @Test
    public void testProfileTableCols() {
        Cursor c = db.query(DatabaseHelper.TABLE_PROFILE, null, null, null, null, null, null);
        assertNotNull( c );

        String[] cols = c.getColumnNames();
        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_LOGIN,
                cols, hasItemInArray(DatabaseHelper.COLUMN_LOGIN));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_NAME,
                cols, hasItemInArray(DatabaseHelper.COLUMN_NAME));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_FOLLOWING,
                cols, hasItemInArray(DatabaseHelper.COLUMN_FOLLOWING));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_FOLLOWERS,
                cols, hasItemInArray(DatabaseHelper.COLUMN_FOLLOWERS));

        assertThat("Column not implemented: " + DatabaseHelper.COLUMN_REPOS,
                cols, hasItemInArray(DatabaseHelper.COLUMN_REPOS));

        c.close();
    }

    @Test
    public void testDBDelete(){
        assertTrue(context.deleteDatabase(DatabaseHelper.DATABASE_NAME));
    }

}