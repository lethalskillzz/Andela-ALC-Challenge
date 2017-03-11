package com.lethalskillzz.andelaalcchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lethalskillzz.andelaalcchallenge.dao.ProfileDataSource;
import com.lethalskillzz.andelaalcchallenge.helper.DatabaseHelper;
import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lethalskillzz on 11/03/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ProfileDataSourceTest {

    private ProfileDataSource dao;
    private ProfileDataSource daoWMocks;
    private SQLiteDatabase dbM;
    private Cursor cM;
    private ProfileItem profileItemMock;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        dao = new ProfileDataSource(context);

        setupNoteMock();
        daoWMocks = new ProfileDataSource(context);
    }

    /**
     * Setup a DBSchema mock
     * @return  Returns the mocked obj
     */
    private DatabaseHelper setupHelperMock(){
        // create the mocks
        DatabaseHelper helperM = mock(DatabaseHelper.class);
        dbM = mock(SQLiteDatabase.class);

        // Define method's results for the mock obj
        when(helperM.getReadableDatabase()).thenReturn(dbM);
        when(helperM.getWritableDatabase()).thenReturn(dbM);
        return helperM;
    }

    /**
     * Setup a mock Profile Item to be used in the tests
     */
    private final String login = "MockNote";
    private final String name = "00/00/00";
    private final String following = "MockNote";
    private final String followers = "00/00/00";
    private final String public_repos = "MockNote";

    private void setupNoteMock() {
        profileItemMock = mock(ProfileItem.class);
        when(profileItemMock.getLogin()).thenReturn(login);
        when(profileItemMock.getName()).thenReturn(name);
        when(profileItemMock.getFollowing()).thenReturn(following);
        when(profileItemMock.getFollowers()).thenReturn(followers);
        when(profileItemMock.getPublicRepos()).thenReturn(public_repos);
    }

    /**
     * Setup a mock Cursor
     * that returns a given Note data
     */
    private void setupMockCursor(ProfileItem profileItemToRet){
        // create the mock cursor
        cM = mock(Cursor.class);
        // define method's return
        when(cM.moveToFirst()).thenReturn(true);

        int idLogin = 0;
        int idName = 1;
        int idFollowing = 2;
        int idFollowers = 3;
        int idRepos = 4;

        // define method's return
        when(cM.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOGIN)).thenReturn(idLogin);
        when(cM.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)).thenReturn(idName);
        when(cM.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOLLOWING)).thenReturn(idFollowing);
        when(cM.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOLLOWERS)).thenReturn(idFollowers);
        when(cM.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPOS)).thenReturn(idRepos);

        // define method's return
        String mLogin = profileItemToRet.getLogin();
        String mName = profileItemToRet.getName();
        String mFollowing = profileItemToRet.getFollowing();
        String mFollowers = profileItemToRet.getFollowers();
        String mPublicRepos = profileItemToRet.getPublicRepos();

        when(cM.getString(idLogin)).thenReturn(mLogin);
        when(cM.getString(idName)).thenReturn(mName);
        when(cM.getString(idFollowing)).thenReturn(mFollowing);
        when(cM.getString(idFollowers)).thenReturn(mFollowers);
        when(cM.getString(idRepos)).thenReturn(mPublicRepos);
    }

    /**
     * Setup a mockDB query search.
     * @param profileItemMockId    ProfileItem id to search
     * @param cursor        Query result
     */
    private void setupQueryMock(int profileItemMockId, Cursor cursor) {
        String[] selArgs = new String[] { Integer.toString(profileItemMockId)};
        // Defining DB query return
        when(dbM.query(
                anyString(),
                (String[]) isNull(),
                anyString(),
                aryEq(selArgs),
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(cursor);
    }


    /**
     * Testing DAO with a db mock
     */
    @Test
    public void getNoteTestMock(){

        setupMockCursor(profileItemMock);
        setupQueryMock(profileItemMock.getLogin(), cM);

        Note note = daoWMocks.getNote(noteMock.getId());
        // Verify if specified method was called
        verify(cM).moveToFirst();
        assertNotNull(note);
        assertEquals(note.getId(), noteMock.getId());
        assertEquals(note.getText(), noteMock.getText());
        assertEquals(note.getDate(), noteMock.getDate());
        // Verify if specified method was called
        verify(cM).close();
        verify(dbM).close();
    }

    /**
     * Testing DAO with a db mock
     */
    @Test
    public void getNoteTestMockFail(){
        setupQueryMock(noteMock.getId(), null);
        Note note = daoWMocks.getNote(noteMock.getId());
        // result should be null
        assertNull(note);
        // should close db
        verify(dbM).close();
    }

}
