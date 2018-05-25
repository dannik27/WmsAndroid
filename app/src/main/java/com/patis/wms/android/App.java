package com.patis.wms.android;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patis.wms.android.service.BackendApi;
import com.patis.wms.android.service.LocalData;
import com.patis.wms.android.service.SqliteHelper;
import com.patis.wms.android.service.gson.DateAdapter;
import com.patis.wms.android.service.GetFromCustomer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static Context context;
    private static Retrofit retrofit;
    private static LocalData localData;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        SqliteHelper sqliteHelper = new SqliteHelper(context);
        localData = new LocalData(sqliteHelper);

    }

    public static BackendApi getBackendApi(){
        return getRetrofit().create(BackendApi.class);
    }

    public static GetFromCustomer getFromCustomerApi(){
        return getRetrofit().create(GetFromCustomer.class);
    }

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl("http://kokoserver.me:8090/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Context getContext() {
        return context;
    }
    public static LocalData local(){
        return localData;
    }
}
