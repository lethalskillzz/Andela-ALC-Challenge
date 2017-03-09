package com.lethalskillzz.andelaalcchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lethalskillzz on 05/03/2017.
 */


public class UserItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("url")
    @Expose
    private String url;


    public UserItem() {}

    public UserItem(String id, String login, String avatarUrl, String url) {

        super();
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.url = url;

    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }


    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }


    public String getAvatarUrl() { return avatarUrl; }

    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }


    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }


}
