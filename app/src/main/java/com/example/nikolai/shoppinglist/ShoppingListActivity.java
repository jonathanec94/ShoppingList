package com.example.nikolai.shoppinglist;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener  {
    ListView list;
    List<String> shoppingListDeatails;
    ArrayList<ShoppingListDetail> loadedShoppingListDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);


        Facade.getInstance().setContext(this);
       Facade.getInstance().openDB();

        TextView headline = (TextView)findViewById(R.id.textView_shoppingListHeadline);
      ShoppingList shoppingList = Facade.getInstance().findShoppingList(Facade.getInstance().getSelectedShoppingList());
        headline.setText(shoppingList.getName());


        list = (ListView)findViewById(R.id.listView_detail);
       loadShoppingListDetails();


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

    public  void loadShoppingListDetails()
    {
        shoppingListDeatails = new ArrayList<>();

        loadedShoppingListDetails = Facade.getInstance().LoadshoppingListDetail();

        if(loadedShoppingListDetails.size() != 0)
        {
          for(int i = 0; i < loadedShoppingListDetails.size(); i++)
          {
          shoppingListDeatails.add(loadedShoppingListDetails.get(i).getProduct());
          }
        }

        updateList();
    }

    private void updateList()
    {

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingListDeatails));
        list.setOnItemClickListener(this);
    }


    public void createItemInShoppingList(View view)
    {
        EditText mEdit;
        mEdit = (EditText)findViewById(R.id.text_addItem);
        Facade.getInstance().createDetail(mEdit.getText().toString());
        loadShoppingListDetails();

    }

    public void DeleteShoppinglist(View view)
    {
     Facade.getInstance().deleteShoppinglist();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
