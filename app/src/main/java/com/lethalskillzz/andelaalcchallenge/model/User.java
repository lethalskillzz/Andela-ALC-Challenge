package com.lethalskillzz.andelaalcchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lethalskillzz on 07/03/2017.
 */

public class User {

    @SerializedName("items")
    @Expose
    private List<UserItem> items = null;


    public List<UserItem> getItems() {
        return items;
    }


    public void setItems(List<UserItem> items) {
        this.items = items;
    }
}
