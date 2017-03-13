package com.lethalskillzz.andelaalcchallenge;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

/**
 * Created by ibrahimabdulkadir on 13/03/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ProfileActivityTest {

    private ProfileActivityTest activity;
    private ActivityController<ProfileActivityTest> controller;
    private MVP_Main.ProvidedPresenterOps presenterOps;
    private StateMaintainer stateMaintainer;


    @Before
    public void before(){
        controller = Robolectric.buildActivity(MainActivity.class);
        presenterOps = Mockito.mock(MVP_Main.ProvidedPresenterOps.class);
        stateMaintainer = Mockito.mock(StateMaintainer.class);
    }

    // testing Activity onCreate
    @Test
    public void testOnCreate(){
        activity = controller.get();

        when(stateMaintainer.firstTimeIn())
                .thenReturn(true);
        activity.mStateMaintainer = stateMaintainer;

        controller.create();

        Mockito.verify(stateMaintainer, VerificationModeFactory.atLeast(2))
                .put(Mockito.any(MainPresenter.class));
        Mockito.verify(stateMaintainer, VerificationModeFactory.atLeast(2))
                .put(Mockito.any(MainModel.class));

        assertNotNull(activity.mPresenter);

    }

    // testing Activity onCreate during a reconfiguration
    @Test
    public void testOnCreateReconfig(){
        controller = Robolectric.buildActivity(MainActivity.class);
        activity = controller.get();

        when(stateMaintainer.firstTimeIn())
                .thenReturn(false);
        when(stateMaintainer.get(MainPresenter.class.getName()))
                .thenReturn(presenterOps);
        activity.mStateMaintainer = stateMaintainer;

        Bundle savedInstanceState = new Bundle();
        controller
                .create(savedInstanceState)
                .start()
                .restoreInstanceState(savedInstanceState)
                .postCreate(savedInstanceState)
                .resume()
                .visible();

        verify(stateMaintainer).get(MainPresenter.class.getName());
        verify(presenterOps).setView(any(MVP_Main.RequiredViewOps.class));

    }

    // testing Activity onDestroy
    @Test
    public void testOnDestroy(){
        controller.create();
        activity = controller.get();
        activity.mPresenter = presenterOps;

        controller.destroy();
        Mockito.verify(presenterOps).onDestroy(Mockito.anyBoolean());

    }
}
