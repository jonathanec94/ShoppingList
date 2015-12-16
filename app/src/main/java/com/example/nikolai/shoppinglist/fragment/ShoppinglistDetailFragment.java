package com.example.nikolai.shoppinglist.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolai.shoppinglist.MainActivity;
import com.example.nikolai.shoppinglist.R;
import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;

import java.util.ArrayList;
import java.util.List;

public class ShoppinglistDetailFragment extends ListFragment {
    List<String> shoppingListDeatails;
    ArrayList<ShoppingListDetail> loadedShoppingListDetails;
    ArrayAdapter<String> adapter;
    public ShoppinglistDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Facade.getInstance().setContext(getActivity());
        Facade.getInstance().openDB();




        loadShoppingListDetails();
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

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, shoppingListDeatails);
        setListAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.shopping_list_detail_fragment, container, false);

        TextView headline = (TextView) rootView.findViewById(R.id.textView_shoppingListHeadline);
          ShoppingList shoppingList = Facade.getInstance().findShoppingList(Facade.getInstance().getSelectedShoppingList());
        headline.setText(shoppingList.getName());
        return rootView;
    }
    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        new AlertDialog.Builder(getActivity())
                .setTitle(shoppingListDeatails.get(position))
                .setMessage(getString(R.string.deleteItemMsg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if(Facade.getInstance().deleteShoppinglistDetail(loadedShoppingListDetails.get(position)._id)){
                            loadShoppingListDetails();
                        }
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


