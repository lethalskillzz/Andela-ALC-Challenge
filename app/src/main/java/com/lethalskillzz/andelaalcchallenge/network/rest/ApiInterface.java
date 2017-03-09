package com.lethalskillzz.andelaalcchallenge.network.rest;

import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;
import com.lethalskillzz.andelaalcchallenge.model.User;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.GET_USERS;

/**
 * Created by lethalskillzz on 9/14/2016.
 */
public interface ApiInterface {

    //@Headers("Authorization :" + AUTHORIZATION)
    @GET(GET_USERS)
    Observable<User> getUsers();


    //@Headers("Authorization :" + AUTHORIZATION)
    @GET
    Observable<ProfileItem> getProfile(@Url String url);
}
