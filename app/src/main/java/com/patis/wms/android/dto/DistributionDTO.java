package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.Product;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class DistributionDTO implements Serializable{

    private long id;
    private int count;
    private boolean done;
    private Product product;
    private StorehouseCellDTO cell; // remove product;


}
