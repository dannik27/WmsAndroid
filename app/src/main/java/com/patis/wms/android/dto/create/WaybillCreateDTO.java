package com.patis.wms.android.dto.create;

import com.patis.wms.android.dto.TransportCompanyDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class WaybillCreateDTO {

    private long id;
    private String info;
    private long transportCompanyId;


}
