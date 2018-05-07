package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorehouseCellDTO{

    private long id;
    private String name;
    private float capacity;
    private float busy;
    private Product product;



}
