package com.patis.wms.android.service;

import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.create.TransportationCreateDTO;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetFromCustomer {

    @POST("getFromCustomer/start/")
    Call<Void> start(@Body RequestCreateDTO requestCreateDTO);

    @POST("getFromCustomer/createTransportation")
    Call<Void> createTransportation(@Body TransportationCreateDTO transportationCreateDTO);

    @POST("getFromCustomer/receiveTransportation/{id_transportation}")
    Call<Void> receiveTransportation(@Body Date date, @Path("id_transportation") long transportationId);
}
