package com.example.forums_backend.config.route.constant;

public class ClientRoute {
    public static final String PREFIX_CLIENT_ROUTE = "/api";

    //posts router
    public  static final String POST_CLIENT_PATH = "/posts";
    public static final String POST_UP_VOTE_CLIENT_PATH = "/posts/{id}/up-vote";
    public static final String POST_DOWN_VOTE_CLIENT_PATH = "/posts/{id}/down-vote";
    public static final String POST_CLIENT_ROUTE = PREFIX_CLIENT_ROUTE.concat(POST_CLIENT_PATH);
    //tags router
    public static final String TAG_CLIENT_PATH = "/tags";
    public static final String TAG_CLIENT_ROUTE = PREFIX_CLIENT_ROUTE.concat(TAG_CLIENT_PATH);



}
