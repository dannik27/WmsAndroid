package com.patis.wms.android.dto.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class Product implements Serializable{

    private long id;
    private String name;
    private String description;
    private float volume;

    @Override
    public String toString() {
        return name;
    }
}
