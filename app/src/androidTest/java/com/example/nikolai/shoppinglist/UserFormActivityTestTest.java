package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.domain.Facade;

/**
 * Created by Jonathan on 12-01-2016.
 */
public class UserFormActivityTestTest  extends ActivityUnitTestCase<UserFormActivity> {

    private UserFormActivity activity;

    Button loginButton;
    Button createButton;
    ContextThemeWrapper context;
    private Intent launchIntent;
    public UserFormActivityTestTest() {
        super(UserFormActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
         context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
        Log.d("MainActivityTestTest", "setUp");

        launchIntent = new Intent(
                getInstrumentation().getTargetContext(),
                UserFormActivity.class
        );
        startActivity(launchIntent, null, null);


        loginButton = (Button) getActivity().findViewById(R.id.button_login);;
        createButton = (Button) getActivity().findViewById(R.id.button_createUser);



    }

    public void testPreconditions() {
        Log.d("UserFormActivityTest", "testPreconditions");
        assertNotNull(getActivity());
        assertNotNull(loginButton);
        assertNotNull(createButton);
    }

    public void testButtonLabel() {
        String labelLogin = getActivity().getString(R.string.login);
        String labelCreate = getActivity().getString(R.string.create);
        Log.d("labelLogin", labelLogin);
        Log.d("labelCreate", labelCreate);
        assertEquals(labelLogin, loginButton.getText());
        assertEquals(labelCreate, createButton.getText());
    }



}
