package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Jonathan on 12-01-2016.
 */
public class UserFormActivityTestTest  extends ActivityUnitTestCase<UserFormActivity> {

    private UserFormActivity activity;

    Button loginButton;
    Button createButton;

    public UserFormActivityTestTest() {
        super(UserFormActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d("MainActivityTestTest", "setUp");

        activity = getActivity();

        loginButton = (Button)activity.findViewById(R.id.button_login);
        createButton = (Button)activity.findViewById(R.id.button_createUser);

    }

    public void testPreconditions() {
        Log.d("UserFormActivityTest", "testPreconditions");
        assertNotNull(activity);
        assertNotNull(loginButton);
        assertNotNull(createButton);
    }

    public void testButtonLabel() {
        String labelLogin = activity.getString(R.string.login);
        String labelCreate = activity.getString(R.string.add_user);
        Log.d("labelLogin", labelLogin);
        Log.d("labelCreate", labelCreate);
        assertEquals(labelLogin, loginButton.getText());
        assertEquals(labelCreate, createButton.getText());
    }
}
