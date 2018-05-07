package com.patis.wms.android.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor
public class Product {

    private long id;
    private String name;
    private String description;
    private float volume;

}
