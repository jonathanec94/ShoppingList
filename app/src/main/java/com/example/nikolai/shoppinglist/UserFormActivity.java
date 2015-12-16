package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.domain.Facade;

public class UserFormActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_form);
    }

   public void createUser(View view)
   {
       EditText userName, password;
       userName = (EditText)findViewById(R.id.editText_username);
       password = (EditText)findViewById(R.id.editText_password);
       Facade.getInstance().createUser(userName.getText().toString(),password.getText().toString());
       userName.setText("");
       password.setText("");
       TextView mEdit;
       mEdit = (TextView)findViewById(R.id.textView_loginStatus);
       mEdit.setText(getString(R.string.createUserError));

       Intent intent = new Intent(this,MainActivity.class);
       startActivity(intent);
   }

    public  void  userLogon(View view)
    {
        EditText userName, password;
        userName = (EditText)findViewById(R.id.editText_username);
        password = (EditText)findViewById(R.id.editText_password);

        boolean login = Facade.getInstance().userLogon(userName.getText().toString(),password.getText().toString());

        if(!login)
        {
            password.setText("");
       //     userName.setText("");
            TextView mEdit;
            mEdit = (TextView)findViewById(R.id.textView_loginStatus);
            mEdit.setText(getString(R.string.loginError));
        }
        else
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }


    }

}
