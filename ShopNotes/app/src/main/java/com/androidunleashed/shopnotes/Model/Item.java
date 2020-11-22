package com.androidunleashed.shopnotes.Model;

public class Item {
    int id;
    String ItemName;
    int ItemQty;
    String Date;

    public Item() {
        //
    }

    public Item(int id, String itemName, int itemQty, String date) {
        this.id = id;
        ItemName = itemName;
        ItemQty = itemQty;
        Date = date;
    }

    public Item(String itemName, int itemQty, String date) {
        ItemName = itemName;
        ItemQty = itemQty;
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemQty() {
        return ItemQty;
    }

    public void setItemQty(int itemQty) {
        ItemQty = itemQty;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
