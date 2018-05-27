package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.OperationType;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Data @NoArgsConstructor
public class RequestDTO {

    private long id;
    private WorkerDTO author;
    private StorehouseDTO storehouseFrom;
    private StorehouseDTO storehouseTo;
    private Date dateBegin;
    private CustomerDTO customer;
    private OperationType operationType;

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        return operationType.toString() +" "+ (customer == null ? "" : customer.getCompany().getName()) +" "+ dateFormat.format(dateBegin);
    }
}
