package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseItemsNotInShelf extends ResponseObject{
    private ArrayList<ItemNotInShelf> data;

    public ResponseItemsNotInShelf(boolean success, String message, ArrayList<ItemNotInShelf> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<ItemNotInShelf> getData() {
        return data;
    }
}
