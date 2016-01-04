package com.example.nikolai.shoppinglist.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.R;
import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;

public class ShoppinglistUserFragment extends ListFragment {
    ArrayAdapter<String> adapter;
    public ShoppinglistUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] values = new String[] { "Speed:", "Direction:", "Bearing:",
                "Latitude:", "Longitude:" };
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.shopping_list_users_fragment, container, false);


        TextView headline = (TextView) rootView.findViewById(R.id.textView_shoppingListHeadline);
        ShoppingList shoppingList = Facade.getInstance().findShoppingList(Facade.getInstance().getSelectedShoppingList());
        headline.setText(shoppingList.getName());

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        new AlertDialog.Builder(getActivity())
                .setTitle("udskriv user name her")
                .setMessage(getString(R.string.deleteItemMsg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                     // slet user forbindelsen her
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

