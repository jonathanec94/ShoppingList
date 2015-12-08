package com.example.nikolai.shoppinglist.domain;

import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import com.example.nikolai.shoppinglist.R;
import com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDb;
import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;

import java.util.ArrayList;

import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.*;


/**
 * Created by Jonathan on 04-12-2015.
 */
public class Facade {
    private static Facade instance;
    private ShoppingListDb db;
    ArrayList<ShoppingList> shoppingLists;
    ArrayList<ShoppingListDetail> shoppingListDetail;
    Cursor cursor;
    Context context;
    int selectedShoppingList;
    private Facade(){

    }
    public void setContext(Context context)
    {
        this.context = context;
        db = new ShoppingListDb(context);
    }

    public void setSelectedShoppingList(int id)
    {
        selectedShoppingList = id;
    }
    public int getSelectedShoppingList()
    {
        return selectedShoppingList;
    }

    public ShoppingList findShoppingList(int id)
    {
        for(int i = 0; i < shoppingLists.size(); i++)
        {
            if(shoppingLists.get(i).getId() == (id))
            {
                return shoppingLists.get(i);
            }
        }
        return null;
    }

    public void openDB(){
        db.open();
    }

    public void loadShoppingLists()
    {
        shoppingLists = new ArrayList<>();
        cursor = db.getShoppingLists();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            shoppingLists.add(new ShoppingList(cursor.getInt(cursor.getColumnIndex(list_ID_COLUMN)),cursor.getString(cursor.getColumnIndex(list_NAME_COLUMN)),cursor.getString(cursor.getColumnIndex(list_DATO_COLUMN)),cursor.getString(cursor.getColumnIndex(list_user_fk_COLUMN))));
            cursor.moveToNext();
        }
        cursor.close();
   // return shoppingLists;
    }

    public  ArrayList<ShoppingListDetail> LoadshoppingListDetail()
    {

        shoppingListDetail = new ArrayList<>();
       cursor = db.getDetails(selectedShoppingList);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            shoppingListDetail.add(new ShoppingListDetail(cursor.getInt(cursor.getColumnIndex(detail_ID_COLUMN)),cursor.getString(cursor.getColumnIndex(detail_product_COLUMN)),cursor.getInt(cursor.getColumnIndex(detail_list_fk_COLUMN))));
            cursor.moveToNext();
        }
        cursor.close();
        return  shoppingListDetail;
    }




    public ArrayList<ShoppingList> getShoppingLists()
    {
        return shoppingLists;
    }

    public void createShoppingList(String name)
    {
        db.createShoppingList(name, "04-12-2015", "0");
    }

    public void createDetail(String product) {db.createDetail(product, selectedShoppingList);}
    public  void deleteShoppinglist()
    {
        db.deleteShoppinglist(selectedShoppingList);
    }





    public static Facade getInstance() {
        if (instance == null ) {
            synchronized (Facade.class) {
                if (instance == null) {
                    instance = new Facade();
                }
            }
        }
        return instance;
    }

}
