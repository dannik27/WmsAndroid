package com.patis.wms.android.service;

import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by danya on 02.11.2017.
 */

public interface BackendApi {



    @POST("fjsdfkjs/")
    Call send(@Body RequestCreateDTO dto);

    @GET("request/")
    Call<List<RequestDTO>> getRequests();
}
