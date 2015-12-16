package com.example.nikolai.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nikolai.shoppinglist.domain.Facade;

import com.example.nikolai.shoppinglist.entity.ShoppingList;
import com.example.nikolai.shoppinglist.entity.ShoppingListDetail;
import com.example.nikolai.shoppinglist.fragment.ShoppinglistDetailFragment;
import com.example.nikolai.shoppinglist.fragment.ShoppinglistUserFragment;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailActivity extends MenuActivity implements OnItemClickListener  {
    ListView list;
    List<String> shoppingListDeatails;
    ArrayList<ShoppingListDetail> loadedShoppingListDetails;
    ShoppinglistDetailFragment detailFragment = new ShoppinglistDetailFragment();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_detail_menu);
        Facade.getInstance().setContext(this);
        Facade.getInstance().openDB();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //list = (ListView)findViewById(android.R.id.list);
        //loadShoppingListDetails();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(detailFragment, "List Details");
        adapter.addFragment(new ShoppinglistUserFragment(), "Users");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
    }

/*
    private void updateList()
    {

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingListDeatails));
        list.setOnItemClickListener((OnItemClickListener) this);
    }

*/
    public void createItemInShoppingList(View view)
    {
        EditText mEdit;
        mEdit = (EditText)findViewById(R.id.text_addItem);

        if(mEdit.getText().toString().length() > 0 ) {
            Facade.getInstance().createDetail(mEdit.getText().toString());
            mEdit.setText("");
            detailFragment.loadShoppingListDetails();
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
                        if(Facade.getInstance().deleteShoppinglistDetail(loadedShoppingListDetails.get(position)._id)){
                            shoppingListDeatails.remove(position);
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
