package com.patis.wms.android.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorehouseCellCreateDTO {

    private long id;
    private String name;
    private float capacity;
    private float busy;
    private long id_product;




}
