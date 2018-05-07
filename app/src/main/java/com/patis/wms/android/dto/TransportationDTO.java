package com.patis.wms.android.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransportationDTO {

    private long id;
    private float grossWeight;
    private Date dateShipped;
    private Date dateReceived;
    private RequestDTO request;
    private WaybillDTO waybill;
    private PackingListDTO packingList;

    private long id_task_in;
    private long id_task_out;


}
