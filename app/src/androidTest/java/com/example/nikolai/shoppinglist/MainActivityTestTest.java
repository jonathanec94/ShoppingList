package com.example.nikolai.shoppinglist;

import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.Button;



/**
 * Created by Jonathan on 12-01-2016.
 */
public class MainActivityTestTest extends ActivityUnitTestCase<MainActivity> {
private MainActivity main;
    private Button button;

            public MainActivityTestTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d("MainActivityTestTest", "setUp");

        main = getActivity();
        button = (Button)main.findViewById(R.id.bottom_CreateShoppingList);
    }

    public void testPreconditions() {
        Log.d("MainActivityTestTest", "testPreconditions");
        assertNotNull(main);
        assertNotNull(button);
    }

    public void testButtonLabel() {
        String label = main.getString(R.string.create);
        Log.d("testButtonLabel", label);
        assertEquals(label, button.getText());
    }


}