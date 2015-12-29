package com.example.nikolai.shoppinglist.dataSourceLayer.server;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.example.nikolai.shoppinglist.MainActivity;
import com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDb;
import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.TABLE_Details;
import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.detail_ID_COLUMN;
import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.detail_list_fk_COLUMN;
import static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.detail_product_COLUMN;

/**
 * Created by nikolai on 16-12-2015.
 */
public class ServerDb {
    private final Context context;
    private ShoppingListDb db;
    Cursor cursor;
    public ServerDb(Context context){
        this.context = context;
        db = new ShoppingListDb(context);
        db.open();

    }



    //public ArrayList<ShoppingListDetail> getListsFromUsername(ArrayList<ShoppingListDetail> local,String username) {
    public void getListsFromUsername(String username, final ArrayList<ShoppingList> shoppingLists) {

        $.ajax(new AjaxOptions().url("http://android-testnikolai1.rhcloud.com/api/findAllListOnUser/" + username)
                .type("GET")
                .dataType("json")
                .debug(true)
                .context(context)
                .success(new Function() {
                    ArrayList<ShoppingListDetail> itemsFromLocal;


                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONArray json = (JSONArray) params[0];

                        try {
                            //get items from server
                            for (int j = 0; j < json.length(); j++) {
                                Map<String, ?> map = $.map(json.getJSONObject(j));
                                JSONArray datas = (JSONArray) map.get("list");

                                boolean statusOnList = Boolean.valueOf(map.get("status").toString());


                                //  Log.e("Ajax-get-title", map.get("title").toString());

                                //Check local up to server
                                for (int l = 0; l < shoppingLists.size(); l++) {
                                    if (shoppingLists.get(l).getName().equalsIgnoreCase(map.get("title").toString())) {
                                        if(!statusOnList){
                                             //Log.e("Ajax", ""+shoppingLists.get(l).getName());
                                             db.deleteShoppinglist(shoppingLists.get(l).getId());
                                            Intent intent = new Intent(context, MainActivity.class);
                                            context.startActivity(intent);
                                        }else {

                                            itemsFromLocal = shoppingListDetails(shoppingLists.get(l).getId());
                                            for (int i = 0; i < datas.length(); i++) {

                                                JSONObject jdata = (JSONObject) datas.get(i);
                                                Map<String, ?> data = $.map(jdata);

                                                String itemTitle = data.get("item").toString();
                                                boolean statusItem = Boolean.valueOf(data.get("status").toString());

                                                boolean doItemExist = false;
                                                //check local itmes in list
                                                for(int m =0; m<itemsFromLocal.size();m++){
                                                    if(itemTitle.equalsIgnoreCase(itemsFromLocal.get(m).product)){
                                                       if(!statusItem){
                                                           db.deleteShoppinglistDetail(itemsFromLocal.get(m)._id);
                                                       }
                                                        doItemExist = true;
                                                        break;
                                                    }
                                                }
                                                //if item in list do not exist the create in db
                                                if(!doItemExist){
                                                    db.createDetail(itemTitle,shoppingLists.get(l).getId());
                                                }

                                            }
                                        }
                                            //item title found - then break
                                            break;
                                    }
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        //droidQuery.alert(response.toString());
                    }
                }).error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("Ajax", statusCode + " " + error);
                    }
                }));


    }

    public ArrayList<ShoppingListDetail> shoppingListDetails(int shoppingFk){
       ArrayList<ShoppingListDetail> shoppingListDetail = new ArrayList<>();
        cursor = db.getDetails(shoppingFk);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            shoppingListDetail.add(new ShoppingListDetail(cursor.getString(cursor.getColumnIndex(detail_ID_COLUMN)),cursor.getString(cursor.getColumnIndex(detail_product_COLUMN)),cursor.getInt(cursor.getColumnIndex(detail_list_fk_COLUMN))));
            cursor.moveToNext();
        }
        cursor.close();
        return  shoppingListDetail;

    }


    //!!!!!!!!!!!!!------POST---------!!!!!!!!
    public void createList(String user,String title) {
        final String data = "{\"users\": {\"username\":\""+user+"\"},\"title\": \""+title+"\",\"status\":true}";
        $.ajax(new AjaxOptions()
                .url("http://android-testnikolai1.rhcloud.com/api/createList")
                .type("POST")
                .dataType("JSON")
                .data(data)

                .context(context)
                .contentType("application/json")
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];
                        Log.e("ServerDb-createList-suc", response.toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("ServerDb-createList-err", statusCode + " " + error);
                    }
                }).debug(true));

    }


    public void createItemInList(String listTitle,String listUsername,String item) {
        final String data = "{\"item\":\""+item+"\",\"size\":1}";
        $.ajax(new AjaxOptions()
                .url("http://android-testnikolai1.rhcloud.com/api/addItemToList/" + listTitle + "/"+listUsername+"")
                .type("POST")
                .dataType("JSON")
                .data(data)
                .context(context)
                .contentType("application/json")
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];
                        Log.e("ServerDb-cItemInListSuc", response.toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("ServerDb-cItemInListErr", statusCode + " " + error);
                    }
                }).debug(true));

    }



    //!!!!!!!!!!!!!!!!!-----------DELETE--------------!!!!!!!!!!!!!!!!!!!!

    public void deleteItemInList(String listTitle,String listUsername,String itemTitle) {
        $.ajax(new AjaxOptions()
                .url("http://android-testnikolai1.rhcloud.com/api/deleteItemInList/" + listTitle + "/" + listUsername + "/" +itemTitle)
                .type("DELETE")
                .dataType("JSON")
                .context(context)
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];
                        Log.e("ServerDb-DEtemInListSuc", response.toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("ServerDb-DEtemInListErr", statusCode + " " + error);
                    }
                }).debug(true));

    }


    public void deleteList(String listTitle,String listUsername) {
        $.ajax(new AjaxOptions()
                .url("http://android-testnikolai1.rhcloud.com/api/deleteItem/" + listTitle + "/"+listUsername)
                .type("DELETE")
                .dataType("JSON")
                .context(context)
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];
                        Log.e("ServerDb-DEListSuc", response.toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("ServerDb-DEListErr", statusCode + " " + error);
                    }
                }).debug(true));

    }

}
