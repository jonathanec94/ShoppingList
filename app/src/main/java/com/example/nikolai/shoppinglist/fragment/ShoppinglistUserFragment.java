package com.example.nikolai.shoppinglist.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.MainActivity;
import com.example.nikolai.shoppinglist.R;
import com.example.nikolai.shoppinglist.ShoppingListDetailActivity;
import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;
import com.example.nikolai.shoppinglist.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class ShoppinglistUserFragment extends ListFragment {
    ArrayAdapter<String> adapter;
    ShoppinglistUserFragment self  = this;
    public ShoppinglistUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        String[] values = new String[] { "tis:", "tis:", "tis:",
                "tis:", "tis:" };
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, Facade.getInstance().getUsers());
        setListAdapter(adapter);
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.shopping_list_users_fragment, container, false);


        TextView headline = (TextView) rootView.findViewById(R.id.textView_shoppingListHeadline);
        ShoppingList shoppingList = Facade.getInstance().findShoppingList(Facade.getInstance().getSelectedShoppingList());
        headline.setText(shoppingList.getName());

        updateList();
                // Inflate the layout for this fragment
        return rootView;
    }

    public void updateList()
    {
        ShoppingList shoppingList = Facade.getInstance().findShoppingList(Facade.getInstance().getSelectedShoppingList());

        User user = Facade.getInstance().getUser();
        if(user != null) {
            String strUrl = "http://android-testnikolai1.rhcloud.com/api/findOneListOnUser/"+ shoppingList.getName()+"/"+user.getUserName();
            Log.d("shoLisUseFragment",strUrl);
            $.ajax(new AjaxOptions().url(strUrl)
                    .type("GET")
                    .dataType("json")
                    .debug(true)
                    .context(this.getContext())
                    .success(new Function() {
                        ArrayList<String> usernames = new ArrayList<String>();

                        @Override
                        public void invoke($ droidQuery, Object... params) {
                            JSONObject json = (JSONObject) params[0];
                            try {
                                Map<String, ?> map = $.map(json);
                                JSONArray datas = (JSONArray) map.get("users");

                                for (int i = 0; i < datas.length(); i++) {
                                    JSONObject jdata = (JSONObject) datas.get(i);
                                    Map<String, ?> data = $.map(jdata);
                                    String username = data.get("username").toString();
                                    //Log.e("username", username);
                                    usernames.add(username);

                                }
                                // $.with(ShoppinglistUserFragment.this.getContext(), android.R.layout.simple_list_item_1).selectChildren().remove();

                                adapter = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_list_item_1, usernames);
                                setListAdapter(adapter);




                        }

                        catch(
                        JSONException e
                        )

                        {
                            e.printStackTrace();
                        }
                    }
        }).error(new Function() {
                        @Override
                        public void invoke($ droidQuery, Object... params) {
                            int statusCode = (Integer) params[1];
                            String error = (String) params[2];
                            Log.e("Ajax-ShopLisUseFra", statusCode + " " + error);
                        }
                    }));

        }

    }


    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        final String username = ((TextView) v).getText().toString();
        new AlertDialog.Builder(getActivity())
                .setTitle(username)
                .setMessage(getString(R.string.delete_user))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                     // slet user forbindelsen her
                        Facade.getInstance().removeUserFromList(username);
                        updateList();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

