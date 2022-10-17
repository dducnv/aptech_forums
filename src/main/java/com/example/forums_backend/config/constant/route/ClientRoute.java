package com.example.forums_backend.config.constant.route;

public class ClientRoute {
    public static final String PREFIX_CLIENT_ROUTE = "/api";

    //posts router
    //PATH MAPPING CONTROLLER
    public static final String POSTS_CLIENT_PATH             = "/posts";
    public static final String POST_CLIENT_CREATE_POST_PATH  = "/post/new";
    public static final String POST_CLIENT_DETAILS_POST_PATH = "/post/{id}/details";

    public static final String GET_ALL_COMMENT_PATH       = "/comments";
    public static final String FIND_COMMENTS_BY_POST_PATH = "post/{post_id}/comments";
    public static final String COMMENT_POST_PATH          = "/post/{id}/comment";
    public static final String POST_UP_VOTE_CLIENT_PATH   = "/post/{id}/vote";
    public static final String COMMENT_VOTE_PATH          = "/comment/{id}/vote";

    //ANT MATCHES PATH
    public static final String POST_COMMENTS_PATH_ANT_MATCHES = "/post/{\\\\d+}/comments";
    public static final String POST_DETAILS_PATH_ANT_MATCHES  = "/post/{\\\\d+}/details";
    public static final String COMMENT_POST_PATH_ANT_MATCHES  = "/post/{\\\\d+}/comment";
    public static final String POST_VOTE_PATH_ANT_MATCHES     = "/post/{\\\\d+}/vote";
    public static final String COMMENT_VOTE_PATH_ANT_MATCHES  = "/comment/{\\\\d+}/vote";

    //ROUTER ANT MATCHES
    public static final String POST_COMMENTS_ROUTE_ANT_MATCHES = PREFIX_CLIENT_ROUTE.concat(POST_COMMENTS_PATH_ANT_MATCHES);
    public static final String POST_DETAILS_ROUTE_ANT_MATCHES  = PREFIX_CLIENT_ROUTE.concat(POST_DETAILS_PATH_ANT_MATCHES);
    public static final String COMMENT_POST_ROUTE_ANT_MATCHES  = PREFIX_CLIENT_ROUTE.concat(COMMENT_POST_PATH_ANT_MATCHES);
    public static final String POST_VOTE_ROUTE_ANT_MATCHES     = PREFIX_CLIENT_ROUTE.concat(POST_VOTE_PATH_ANT_MATCHES);
    public static final String COMMENT_VOTE_ROUTE_ANT_MATCHES  = PREFIX_CLIENT_ROUTE.concat(COMMENT_VOTE_PATH_ANT_MATCHES);
    public static final String POSTS_CLIENT_ROUTE              = PREFIX_CLIENT_ROUTE.concat(POSTS_CLIENT_PATH);
    public static final String POST_CLIENT_CREATE_POST_ROUTE   = PREFIX_CLIENT_ROUTE.concat(POST_CLIENT_CREATE_POST_PATH);

    //tags router
    public static final String TAG_CLIENT_PATH  = "/tags";
    public static final String TAG_FOLLOW_CLIENT_PATH  = "/tag/follow";
    public static final String TAG_CLIENT_ROUTE = PREFIX_CLIENT_ROUTE.concat(TAG_CLIENT_PATH);
    public static final String TAG_FOLLOW_CLIENT_ROUTE = PREFIX_CLIENT_ROUTE.concat(TAG_FOLLOW_CLIENT_PATH);

    //Comment router




}
