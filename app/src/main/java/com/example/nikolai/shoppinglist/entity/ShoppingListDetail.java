package com.example.nikolai.shoppinglist.entity;

/**
 * Created by Jonathan on 04-12-2015.
 */
public class ShoppingListDetail {

    public String _id;
    public String product;
   // public String image;
  //  public int size;
    public int list_fk;

    public  ShoppingListDetail(String _id, String product, int list_fk)
    {
        this._id = _id;
        this.product = product;
        this.list_fk = list_fk;
    }
    public ShoppingListDetail (){}
    public String getProduct()
    {
        return product;
    }

    public void setProduct(String text){
        product = text;
    }
}
