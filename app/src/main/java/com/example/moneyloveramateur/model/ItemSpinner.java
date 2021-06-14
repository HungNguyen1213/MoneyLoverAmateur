package com.example.moneyloveramateur.model;

public class ItemSpinner {
    private String itemName;
    private int itemIcon;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public ItemSpinner(String itemName, int itemIcon) {
        this.itemName = itemName;
        this.itemIcon = itemIcon;
    }

    public ItemSpinner() {
    }
}
