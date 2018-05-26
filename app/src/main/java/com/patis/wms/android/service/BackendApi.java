package com.patis.wms.android.service;

import com.patis.wms.android.dto.CustomerDTO;
import com.patis.wms.android.dto.PersonDTO;
import com.patis.wms.android.dto.RequestDTO;
import com.patis.wms.android.dto.RequestItemDTO;
import com.patis.wms.android.dto.StorehouseDTO;
import com.patis.wms.android.dto.TaskDTO;
import com.patis.wms.android.dto.TransportationDTO;
import com.patis.wms.android.dto.WorkerDTO;
import com.patis.wms.android.dto.create.RequestCreateDTO;
import com.patis.wms.android.dto.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by danya on 02.11.2017.
 */

public interface BackendApi {





    @GET("request/")
    Call<List<RequestDTO>> getRequests();

    @GET("task/")
    Call<List<TaskDTO>> getTasksByWorker(@Query("id_worker") long id_worker, @Query("current") boolean current);

    @GET("worker/authorization/")
    Call<WorkerDTO> authorization(@Query("login") String login, @Query("password") String password);

    @GET("worker/{id_worker}/")
    Call<WorkerDTO> findWorkerById(@Path("id_worker") long id);

    @POST("task/{id_task}/start/")
    Call<Void> startTask(@Path("id_task") long id_task);

    @POST("task/{id_task}/complete/{id_distribution}/")
    Call<Void> completeDistribution(@Path("id_task") long id_task, @Path("id_distribution") long id_distribution);

    @GET("task/{id_task}/")
    Call<TaskDTO> getTaskById(@Path("id_task") long id_task);

    @GET("customer/")
    Call<List<CustomerDTO>> getCustomers();

    @GET("storehouse/")
    Call<List<StorehouseDTO>> getStorehouses();

    @GET("product/")
    Call<List<Product>> getProducts();

    @GET("request/{id}/")
    Call<RequestDTO> getRequestById(@Path("id") int requestId);

    @GET("request/{id}/item/")
    Call<List<RequestItemDTO>> getRequestItems(@Path("id") int requestId);

    @GET("transportation/")
    Call<List<TransportationDTO>> getTransportations();


}
