package com.patis.wms.android.service;

import com.patis.wms.android.dto.PersonDTO;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.WorkerDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by danya on 02.11.2017.
 */

public interface BackendApi {



    @POST("fjsdfkjs/")
    Call send(@Body RequestCreateDTO dto);

    @GET("request/")
    Call<List<RequestDTO>> getRequests();

    @GET("task/")
    Call<List<TaskDTO>> getTasksByWorker(@Query("id_worker") long id_worker, @Query("current") boolean current);

    @GET("worker/authorization/")
    Call<WorkerDTO> authorization(@Query("login") String login, @Query("password") String password);
}
