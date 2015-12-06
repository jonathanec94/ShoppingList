package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> shoppingLists;
    ListView list;
    Cursor cursor;
    Adapter adapter;
    ArrayList<ShoppingList> loadedShoppingLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Facade.getInstance().setContext(this);
        Facade.getInstance().openDB();
        shoppingLists = new ArrayList<>();


        list = (ListView)findViewById(R.id.listView_detail);

        loadShoppingLists();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public  void loadShoppingLists()
    {
        Facade.getInstance().loadShoppingLists();
        shoppingLists = new ArrayList<>();
        loadedShoppingLists = Facade.getInstance().getShoppingLists();
        for(int i = 0; i < loadedShoppingLists.size(); i++)
        {
            shoppingLists.add(loadedShoppingLists.get(i).getName());
        }
        updateList();
    }

    private void updateList()
    {

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingLists));
        list.setOnItemClickListener(this);
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShoppingListActivity.class);

        Facade.getInstance().setSelectedShoppingList(loadedShoppingLists.get(position).getId());
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
        Facade.getInstance().createShoppingList(mEdit.getText().toString());
        loadShoppingLists();
    }

}