package com.patis.wms.android.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorehouseDTO {

    private long id;
    private String name;
    private String address;


    @Override
    public String toString() {
        return name;
    }
}
