package com.lethalskillzz.andelaalcchallenge.network.rest;

import com.lethalskillzz.andelaalcchallenge.model.UserItem;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.AUTHORIZATION;
import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.GET_USERS;

/**
 * Created by lethalskillzz on 9/14/2016.
 */
public interface ApiInterface {


    @Headers("Authorization :" + AUTHORIZATION)
    @GET(GET_USERS)
    Observable<List<UserItem>> getUsers();
}
