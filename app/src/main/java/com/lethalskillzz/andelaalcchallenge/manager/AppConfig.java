package com.lethalskillzz.andelaalcchallenge.manager;

/**
 * Created by lethalskillzz on 9/5/2016.
 */
public class AppConfig {

    // Github API server
    public static String BASE_URL = "https://api.github.com/";

    // Fetch java users from lagos
    // GET search/users?q=language:java+location:lagos/
    public static final String GET_USERS = "search/users?q=language:java+location:lagos";

    // Fetch user profile
    // GET users/<USERNAME>
    public static final String GET_PROFILE = "users/";


    // Github authorization token
    public static final String AUTHORIZATION = "TruGitHub 1470209121281";

    //
    public static final int handlerClickUser = 0;
    //
    public static final int httpIntentLoadUsers = 1;
    //
    public static final int httpIntentLoadUsersError = 2;
    //
    public static final int httpIntentLoadProfile = 3;

    public static final int VIEW_ITEM_USER = 1;
    public static final int VIEW_PROG = 0;


}
