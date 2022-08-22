package com.example.forums_backend.config.route.constant;

public class AuthRoute {
    public static final String PREFIX_AUTH_ROUTE = "/api/auth";

    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    public static final String USER_INFO_PATH = "/me/info";

    public static final String LOGIN_ROUTE = PREFIX_AUTH_ROUTE.concat(LOGIN_PATH);
    public static final String REGISTER_ROUTE = PREFIX_AUTH_ROUTE.concat(REGISTER_PATH);
    public static final String USER_INFO_ROUTE = PREFIX_AUTH_ROUTE.concat(USER_INFO_PATH);
}
