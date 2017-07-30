package com.farheen.grossary;

import com.farheen.grossary.model.ItemsPojo;

import java.util.ArrayList;

/**
 * Created by Parth Modi on 30/03/2017.
 */

public class Singleton {

    private static Singleton instance = null;

    private ArrayList<ItemsPojo> cartProductsList = new ArrayList<>();

    public ArrayList<ItemsPojo> getCartProductsList() {
        return cartProductsList;
    }

    private String currentFragmentName = "";

    public String getCurrentFragmentName() {
        return currentFragmentName;
    }

    public void setCurrentFragmentName(String currentFragmentName) {
        this.currentFragmentName = currentFragmentName;
    }

    public void setCartProductsList(ArrayList<ItemsPojo> cartProductsList) {
        this.cartProductsList = cartProductsList;
    }

    private static Singleton singleton = new Singleton( );
    private Singleton() {
        // Exists only to defeat instantiation.
    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
