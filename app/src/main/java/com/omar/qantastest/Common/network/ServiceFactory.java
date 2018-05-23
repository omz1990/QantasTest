package com.omar.qantastest.Common.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omar.qantastest.Common.network.handlers.RxErrorHandlingCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by omz on 23/5/18
 */
public class ServiceFactory {
    public static final String SERVICE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static <Service> Service createService(Class<Service> serviceClass, String serviceEndpoint) {

        Gson gson = new GsonBuilder()
                .setDateFormat(SERVICE_DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(serviceEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClientFactory.getOkHttpClient());

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }
}
