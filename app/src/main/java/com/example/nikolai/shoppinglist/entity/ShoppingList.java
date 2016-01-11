package com.example.nikolai.shoppinglist.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 04-12-2015.
 */
public class ShoppingList {
    int id;
    String name;
    String dato;
    String user;
    public ShoppingList(int id, String name, String dato, String user)
    {
        this.id = id;
        this.name = name;
        this.dato = dato;
        this.user = user;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public String getDato()
    {
        return dato;
    }


}
