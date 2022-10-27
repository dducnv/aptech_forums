package com.example.forums_backend.config.constant.route;

public class AccountRoute {
    public static final String PREFIX_ACCOUNT_ROUTE = "/api/account";

    public static final String GET_ALL_PATH = "/get-all";
    public static final String UPDATE_PATH = "/update/\\d";
    public static final String DELETE_PATH = "/delete/\\d";

    public static final String GET_ALL_ACCOUNT_ROUTE = PREFIX_ACCOUNT_ROUTE.concat(GET_ALL_PATH);
    public static final String UPDATE_ACCOUNT_ROUTE = PREFIX_ACCOUNT_ROUTE.concat(UPDATE_PATH);
    public static final String DELETE_ACCOUNT_ROUTE = PREFIX_ACCOUNT_ROUTE.concat(DELETE_PATH);
}
