package com.patis.wms.android.service;

import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.TransportationCreateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetFromCustomer {

    @POST("getFromCustomer/start/")
    Call<Void> start(@Body RequestCreateDTO requestCreateDTO);

    @POST("getFromCustomer/createTransportation")
    Call<Void> createTransportation(@Body TransportationCreateDTO transportationCreateDTO);
}
