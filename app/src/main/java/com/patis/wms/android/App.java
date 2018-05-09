package com.patis.wms.android;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patis.wms.android.service.BackendApi;
import com.patis.wms.android.service.gson.DateAdapter;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static Context context;
    private static BackendApi backend;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static BackendApi getBackendApi(){
        if(backend == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://kokoserver.me:8090/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            backend = retrofit.create(BackendApi.class);



        }
        return backend;

    }

    public static Context getContext() {
        return context;
    }
}
