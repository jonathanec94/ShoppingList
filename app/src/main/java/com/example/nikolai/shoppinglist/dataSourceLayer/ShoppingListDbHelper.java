package com.example.nikolai.shoppinglist.dataSourceLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingListDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE = "datastorage";
    public static final int VERSION = 1;

    private static final String LOG_TAG = "ShoppingListDbHelper";



    // bruger table
    public static final String TABLE_user = "User";
    public static final String user_ID_COLUMN = "_id";
    public static final String user_NAME_COLUMN = "userName";
    public static final String user_PASSWORD_COLUMN = "password";


    private static final String CREATE_TABLE_SQL =
            "create table "+TABLE_user+" ("+
                    user_ID_COLUMN+" integer primary key autoincrement, "+
                    user_NAME_COLUMN+" text not null, "+
                    user_PASSWORD_COLUMN+" integer not null );"

            ;
    private static final String DROP_TABLE_SQL =
            "drop table if exists "+TABLE_user+";";



    //list
    public static final String TABLE_List = "ShoppingList";
    public static final String list_ID_COLUMN = "_id";
    public static final String list_NAME_COLUMN = "name";
    public static final String list_DATO_COLUMN = "shopping_date";
    public static final String list_user_fk_COLUMN = "user_fk";



    private static final String CREATE_TABLE_SQL_list =
            "create table "+TABLE_List+" ("+
                    list_ID_COLUMN+" integer primary key autoincrement, "+
                    list_NAME_COLUMN+" text not null, "+
                    list_DATO_COLUMN+" text not null, "+
                    list_user_fk_COLUMN+","+
                    "FOREIGN KEY("+list_user_fk_COLUMN + ") REFERENCES "+TABLE_user+"("+user_ID_COLUMN+"));";
                   // list_DATO_COLUMN+" text not null );";
    private static final String DROP_TABLE_SQL_list =
            "drop table if exists "+TABLE_List+";";



// list details
    public static final String TABLE_Details = "ShoppingListDetail";

    public static final String detail_ID_COLUMN = "_id";
    public static final String detail_product_COLUMN = "product";
    public static final String detail_image_COLUMN = "image";
    public static final String detail_size_COLUMN = "size";
    public static final String detail_list_fk_COLUMN = "list_fk";



    private static final String CREATE_TABLE_SQL_details =
            "create table "+TABLE_Details+" ("+
                    detail_ID_COLUMN+" integer primary key autoincrement, "+
                    detail_product_COLUMN+" text not null,"+
                 //   detail_image_COLUMN+" text not null, "+
                    detail_list_fk_COLUMN+" integer,"+
                   // detail_size_COLUMN+" text not null,"+
                    "FOREIGN KEY("+detail_list_fk_COLUMN + ") REFERENCES "+TABLE_List+"("+list_ID_COLUMN+"));";

    private static final String DROP_TABLE_SQL_detail = "drop table if exists "+TABLE_Details+";";



    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_SQL);
            db.execSQL(CREATE_TABLE_SQL_list);
            db.execSQL(CREATE_TABLE_SQL_details);

            }
        catch (SQLiteException sqle) {
            Log.e(LOG_TAG, "Could not create database "+DATABASE);
            }
        Log.v(LOG_TAG, "Database " + DATABASE + " created");
        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SQL);
        db.execSQL(DROP_TABLE_SQL_list);
        db.execSQL(DROP_TABLE_SQL_detail);
        onCreate(db);
        }

    }
