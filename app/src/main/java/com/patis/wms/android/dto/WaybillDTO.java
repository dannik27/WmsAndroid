package com.patis.wms.android.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class WaybillDTO {

    private long id;
    private String info;
    private TransportCompanyDTO transportCompany;


}
