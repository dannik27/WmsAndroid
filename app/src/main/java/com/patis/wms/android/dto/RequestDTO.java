package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.OperationType;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data @NoArgsConstructor
public class RequestDTO {

    private long id;
    private WorkerDTO author;
    private StorehouseDTO storehouseFrom;
    private StorehouseDTO storehouseTo;
    private Date dateBegin;
    private CustomerDTO customer;
    private OperationType operationType;

}
