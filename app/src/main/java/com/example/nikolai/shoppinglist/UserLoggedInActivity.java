package com.example.nikolai.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.domain.Facade;

public class UserLoggedInActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        TextView mEdit;
        mEdit = (TextView)findViewById(R.id.loggedOnUserName);
        mEdit.setText(getString(R.string.userName) + ": " +Facade.getInstance().getUser().getUserName());
        }
}
