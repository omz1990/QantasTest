package com.omar.qantastest.Common.network;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by omz on 23/5/18
 */
public class ServerSettings {

    public static class Domain {
        public static final long OKHTTP_CLIENT_READ_TIMEOUT_SECONDS = 6;
        public static final long OKHTTP_CLIENT_CONNECT_TIMEOUT_SECONDS = 6;
        public static final HttpLoggingInterceptor.Level HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;

        public static final String BASE_URL = "https://g525204.github.io/";
    }
}
