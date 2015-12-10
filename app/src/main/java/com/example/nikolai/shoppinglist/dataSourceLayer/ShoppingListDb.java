package com.example.nikolai.shoppinglist.dataSourceLayer;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;

import java.sql.SQLException;

import  static com.example.nikolai.shoppinglist.dataSourceLayer.ShoppingListDbHelper.*;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ShoppingListDb implements AutoCloseable {
	private static final String LOG_TAG = "ShoppingListDB";
	private SQLiteDatabase db;
	private final Context context;
	private final ShoppingListDbHelper helper;

	public ShoppingListDb(Context context) {
		this.context = context;
		helper = new ShoppingListDbHelper(context);
		}
	
	public void close() { db.close(); }
	
	public void open() {
		try {
			db = helper.getWritableDatabase();
			}
		catch (SQLiteException sqle) {
			Log.w(LOG_TAG, "Could not open database for writing "+sqle.getMessage());
			// Log.println(Log.WARN, LOG_TAG, "message... ");
			db = helper.getReadableDatabase();
			}
		}


	public long createShoppingList(String name, String dato, String user_fk)
	{
		try
		{
		ContentValues values = new ContentValues();
		values.put(list_NAME_COLUMN, name);
		values.put(list_DATO_COLUMN, dato);
		values.put(list_user_fk_COLUMN, user_fk);
		return db.insert(TABLE_List, null, values);
		}
		catch (SQLiteException sqle)
		{
			Log.w(LOG_TAG, "Could not create Shopping List "+sqle.getMessage());
			return -1;
		}
	}



	public long createDetail(String product, int list_fk)
	{
		try
		{
			ContentValues values = new ContentValues();
			values.put(detail_product_COLUMN, product);
		//	values.put(detail_size_COLUMN, "0");
		//	values.put(detail_image_COLUMN, "0");
			values.put(detail_list_fk_COLUMN, list_fk);
			return db.insert(TABLE_Details, null, values);
		}
		catch (SQLiteException sqle)
		{
			Log.w(LOG_TAG, "Could not create "+ TABLE_Details + " "+sqle.getMessage());
			return -1;
		}
	}

	public boolean createUser(String userName, String password)
	{
			// create user
			ContentValues values = new ContentValues();
			values.put(user_NAME_COLUMN,userName);
			values.put(user_PASSWORD_COLUMN,password);
			try{
			db.insert(TABLE_user, null, values);
				return true;
			}catch(SQLiteException sqle){
				Log.w(LOG_TAG, "Could not create "+ TABLE_user + " "+sqle.getMessage());
				return false;
		}
	}

	public Cursor getShoppingLists()
	{
		return db.rawQuery("select * from "+TABLE_List, null);
	}
	public Cursor getDetails(int shoppingList_FK){return db.rawQuery("select * from "+TABLE_Details + " where "+detail_list_fk_COLUMN + " = "+shoppingList_FK, null);}
	public void  deleteShoppinglist(int id)
	{
		db.execSQL("DELETE FROM " +TABLE_List + " WHERE "+list_ID_COLUMN +" = "+id +";");
	}

	public boolean  userLogon(String userName, String password)
	{

		Cursor cursor =	db.rawQuery("select * from " + TABLE_user + " where " + user_NAME_COLUMN + " = '" + userName + "' and " + user_PASSWORD_COLUMN + " = '" + password+"'", null);

		if(cursor != null)
		{
			return true;
		}
		else {
			return false;
		}

	}

	
	}
