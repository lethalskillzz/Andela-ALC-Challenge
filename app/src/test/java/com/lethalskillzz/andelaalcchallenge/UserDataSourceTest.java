package com.lethalskillzz.andelaalcchallenge;

import android.content.Context;

import com.lethalskillzz.andelaalcchallenge.dao.UserDataSource;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by lethalskillzz on 12/03/2017.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class UserDataSourceTest {

    private UserDataSource dao;
    private UserItem userReal;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        dao = new UserDataSource(context);
        dao.open();

        //setupProfile();
    }


    @After
    public void cleanup(){
        dao.close();
    }



    private void setupUser() {
        userReal = new UserItem();
        userReal.setId("8193385");
        userReal.setLogin("lethalskillzz");
        userReal.setAvatarUrl("https://avatars3.githubusercontent.com/u/8193385?v=3");
        userReal.setUrl("https://api.github.com/users/lethalskillzz");
    }

    /**
     * Testing Create User
     */
    @Test
    public void createUserTest() {
        setupUser();
        UserItem userInserted = dao.createUser(userReal);
        assertNotNull(userInserted);
        assertEquals(userReal.getLogin(), userInserted.getLogin());
    }


    /**
     * Testing Read All User
     */
    @Test
    public void readAllUserTest() {
        setupUser();
        UserItem userInserted = dao.createUser(userReal);

        List<UserItem> users = dao.readUserByLogin(userInserted.getLogin());
        for(UserItem user : users) {
            assertNotNull(user);
            assertEquals(user.getLogin(), userInserted.getLogin());
        }
    }


    /**
     * Testing Read User By Login
     */
    @Test
    public void readUserByLoginTest() {
        setupUser();
        UserItem userInserted = dao.createUser(userReal);

        List<UserItem> users = dao.readUserByLogin(userInserted.getLogin());
        for(UserItem user : users) {
            assertNotNull(user);
            assertEquals(user.getLogin(), userInserted.getLogin());
        }
    }


    /**
     * Testing Delete User
     */
    @Test
    public void deleteUserTest() {
        setupUser();
        UserItem userInserted = dao.createUser(userReal);

        long delResult = dao.deleteUserItem( userInserted );
        assertEquals(1, delResult);
    }

}
