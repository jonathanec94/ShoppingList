package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.*;

import com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDb;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> shoppingLists;
    ListView list;
    Cursor cursor;
    Adapter adapter;
    private ShoppingListDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ShoppingListDb(this);
        db.open();

//        db.createShoppingList("Nytår ", "31-12-2015", "0");
  //      db.createShoppingList("jul ", "24-12-2015", "0");
    //    db.createShoppingList("Sannes Fødselsdag ", "01-02-2016","0");

        shoppingLists = new ArrayList<>();
        loadShoppingLists();

        updateList();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void  loadShoppingLists()
    {
        cursor = db.getShoppingLists();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            shoppingLists.add(cursor.getString(cursor.getColumnIndex(list_NAME_COLUMN))+" "+cursor.getString(cursor.getColumnIndex(list_ID_COLUMN)));
            cursor.moveToNext();
        }
        cursor.close();
        list = (ListView)findViewById(R.id.listView);
    }

    private void updateList()
    {
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingLists));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        // menu.add("hello").setIcon(R.drawable.newshoping);
        //menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.shopping_cart));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                //newGame();
                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createShoppingList(View view)
    {
        EditText mEdit;
        mEdit = (EditText)findViewById(R.id.text_shoppingList);
        db.createShoppingList(mEdit.getText().toString(),"04-12-2015", "0");
        loadShoppingLists();
        updateList();
    }

}
