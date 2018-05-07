package com.patis.wms.android.dto.create;

import com.patis.wms.android.dto.entity.OperationType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestCreateDTO implements Serializable {

  private long id;
  private long id_author;
  private long id_storehouse_from;
  private long id_storehouse_to;
  private Date dateBegin;
  private long id_customer;
  private OperationType operationType;

  private List<RequestItemCreateDTO> items;


}
