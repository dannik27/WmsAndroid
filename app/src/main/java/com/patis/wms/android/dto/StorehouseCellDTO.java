package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.Product;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorehouseCellDTO implements Serializable{

    private long id;
    private String name;
    private float capacity;
    private float busy;
    private Product product;



}
