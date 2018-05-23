package com.omar.qantastest.Common.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by omz on 23/5/18
 */
public class OkHttpClientFactory {
    public static OkHttpClient getOkHttpClient() {
        return getOkHttpClientBuilder().build();
    }

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .readTimeout(ServerSettings.Domain.OKHTTP_CLIENT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(ServerSettings.Domain.OKHTTP_CLIENT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor());
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(ServerSettings.Domain.HTTP_LOG_LEVEL);
        return loggingInterceptor;
    }
}