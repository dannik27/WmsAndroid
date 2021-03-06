package com.patis.wms.android.dto;


import com.patis.wms.android.dto.entity.OperationType;
import com.patis.wms.android.dto.entity.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TaskDTO implements Serializable{

    private long id;
    private TaskStatus taskStatus;
    private OperationType operationType;
    private List<DistributionDTO> distributions;
    private Date timeBegin;
    private Date timeEnd;
    private WorkerDTO worker;
    private String customerName;
    private long transportationId;
    private String storehouseName;

}
