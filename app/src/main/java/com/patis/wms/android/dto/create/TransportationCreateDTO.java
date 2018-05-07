package com.patis.wms.android.dto.create;

import com.patis.wms.android.dto.PackingListDTO;
import com.patis.wms.android.dto.WaybillDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransportationCreateDTO implements Serializable {

    private long id;
    private float grossWeight;
    private long id_request;

    private WaybillDTO waybill;
    private PackingListDTO packingList;




}
