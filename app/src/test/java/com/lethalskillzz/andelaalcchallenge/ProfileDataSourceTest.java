package com.lethalskillzz.andelaalcchallenge;

import android.content.Context;

import com.lethalskillzz.andelaalcchallenge.dao.ProfileDataSource;
import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by lethalskillzz on 11/03/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ProfileDataSourceTest {

    private ProfileDataSource dao;
    private ProfileItem profileReal;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        dao = new ProfileDataSource(context);
        dao.open();

        //setupProfile();
    }


    @After
    public void cleanup(){
        dao.close();
    }


    private void setupProfile() {
        profileReal = new ProfileItem();
        profileReal.setLogin("lethalskillzz");
        profileReal.setName("Ibrahim Abdulkadir");
        profileReal.setFollowers("23");
        profileReal.setFollowing("10");
        profileReal.setPublicRepos("31");
    }

    /**
     * Testing Create Profile
     */
    @Test
    public void createProfileTest() {
        setupProfile();
        ProfileItem profileInserted = dao.createProfile(profileReal);
        assertNotNull(profileInserted);
        assertEquals(profileReal.getLogin(), profileInserted.getLogin());
    }

    /**
     * Testing Read Profile
     */
    @Test
    public void readProfileTest() {
        setupProfile();
        ProfileItem profileInserted = dao.createProfile(profileReal);

        ProfileItem profile = dao.readProfileByLogin(profileInserted.getLogin());
        assertNotNull(profile);
        assertEquals(profile.getLogin(), profileInserted.getLogin());
    }



    /**
     * Testing Delete Profile
     */
    @Test
    public void deleteProfileTest() {
        setupProfile();
        ProfileItem profileInserted = dao.createProfile(profileReal);

        long delResult = dao.deleteProfileItem( profileInserted );
        assertEquals(1, delResult);
    }

}
