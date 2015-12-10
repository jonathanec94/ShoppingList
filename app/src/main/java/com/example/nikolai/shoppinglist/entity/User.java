package com.example.nikolai.shoppinglist.entity;

/**
 * Created by Jonathan on 09-12-2015.
 */
public class User {
    String userName;
    String password;

    public User( String userName, String password)
    {
        this.userName =userName;
        this.password = password;
    }
    public String getUserName()
    {
        return userName;
    }
}
