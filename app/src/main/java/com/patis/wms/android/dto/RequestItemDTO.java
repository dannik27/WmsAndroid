package com.patis.wms.android.dto;

import com.patis.wms.android.dto.entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestItemDTO {

    private long id;
    private int count;
    private Product product;

}
