package com.androidunleashed.databasesample.Model;

public class Item {

    int Id;
    String ItemName;
    int ItemQty;
    String ItemColor;
    int ItemSize;
    String Date;

    public Item() {

    }

    public Item(int id, String itemName, int itemQty, String itemColor, int itemSize, String date) {
        Id = id;
        ItemName = itemName;
        ItemQty = itemQty;
        ItemColor = itemColor;
        ItemSize = itemSize;
        Date = date;
    }

    public Item(String itemName, int itemQty, String itemColor, int itemSize, String date) {
        ItemName = itemName;
        ItemQty = itemQty;
        ItemColor = itemColor;
        ItemSize = itemSize;
        Date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getItemColor() {
        return ItemColor;
    }

    public void setItemColor(String itemColor) {
        ItemColor = itemColor;
    }

    public int getItemSize() {
        return ItemSize;
    }

    public void setItemSize(int itemSize) {
        ItemSize = itemSize;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
