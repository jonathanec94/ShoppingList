package com.example.nikolai.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ShoppingListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    private static final String LOG_TAG = "ShoppingListActivity";
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
            case R.id.menuUser:
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

        if(mEdit.getText().toString().length() > 0 ) {
            Facade.getInstance().createDetail(mEdit.getText().toString());
            mEdit.setText("");
            loadShoppingListDetails();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.addItemErrorheadLine))
                    .setMessage(getString(R.string.addItemErrorMsg))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {// continue with delete

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    public void DeleteShoppinglist(View view)
    {
     Facade.getInstance().deleteShoppinglist();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle(shoppingListDeatails.get(position))
                .setMessage(getString(R.string.deleteItemMsg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Log.e(LOG_TAG, "continue with delete!");

                        if(Facade.getInstance().deleteShoppinglistDetail(loadedShoppingListDetails.get(position)._id)){
                            shoppingListDeatails.remove(position);
                            updateList();
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        Log.e(LOG_TAG, "do nothing!");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
