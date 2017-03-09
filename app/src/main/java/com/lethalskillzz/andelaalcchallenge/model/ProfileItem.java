package com.lethalskillzz.andelaalcchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lethalskillzz on 09/03/2017.
 */

public class ProfileItem {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("following")
    @Expose
    private String following;

    @SerializedName("followers")
    @Expose
    private String followers;

    @SerializedName("public_repos")
    @Expose
    private String public_repos;


    public ProfileItem() {}

    public ProfileItem(String login, String name, String following, String followers, String public_repos) {

        super();
        this.login = login;
        this.name = name;
        this.following = following;
        this.followers = followers;
        this.public_repos = public_repos;

    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


    public String getFollowing() { return following; }

    public void setFollowing(String following) { this.following = following; }


    public String getFollowers() { return followers; }

    public void setFollowers(String followers) { this.followers = followers; }


    public String getPublicRepos() { return public_repos; }

    public void setPublicRepos(String public_repos) { this.public_repos = public_repos; }

}
