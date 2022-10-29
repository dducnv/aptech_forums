package com.example.forums_backend.config.constant.route;

public class AdminRoute {

    public static final String PREFIX_ADMIN_ROUTE = "/api/admin";

    public static final String ACCOUNT_PATH = "/accounts";
    public static final String ACCOUNT_PATH_WITH_ID = "/accounts/{id}";
    public static final String POST_PATH = "/posts";
    public static final String POST_PATH_WITH_ID = "/posts/{id}";
    public static final String TAG_PATH = "/tags";
    public static final String COMMENT_PATH = "/comments/{id}";

//    public static final String ACCOUNT_ROUTER = PREFIX_ADMIN_ROUTE.concat(ACCOUNT_PATH);
//    public static final String POST_ROUTER = PREFIX_ADMIN_ROUTE.concat(POST_PATH);
//    public static final String TAG_ROUTER = PREFIX_ADMIN_ROUTE.concat(TAG_PATH);
//    public static final String COMMENT_ROUTER = PREFIX_ADMIN_ROUTE.concat(COMMENT_PATH);
}
