package com.androidunleashed.shelper.model;

public class Item {


    private int id;
    private String iName;
    private int iQuantity;
    private int iSize;
    private String iColor;
    private String itemAddedDate;

    public Item() {
        //
    }

    public Item(int id, String iName, int iQuantity, int iSize, String iColor, String itemAddedDate) {
        this.id = id;
        this.iName = iName;
        this.iQuantity = iQuantity;
        this.iSize = iSize;
        this.iColor = iColor;
        this.itemAddedDate = itemAddedDate;
    }

    public Item(String iName, int iQuantity, int iSize, String iColor, String itemAddedDate) {
        this.iName = iName;
        this.iQuantity = iQuantity;
        this.iSize = iSize;
        this.iColor = iColor;
        this.itemAddedDate = itemAddedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public int getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(int iQuantity) {
        this.iQuantity = iQuantity;
    }

    public int getiSize() {
        return iSize;
    }

    public void setiSize(int iSize) {
        this.iSize = iSize;
    }

    public String getiColor() {
        return iColor;
    }

    public void setiColor(String iColor) {
        this.iColor = iColor;
    }

    public String getItemAddedDate() {
        return itemAddedDate;
    }

    public void setItemAddedDate(String itemAddedDate) {
        this.itemAddedDate = itemAddedDate;
    }

}
