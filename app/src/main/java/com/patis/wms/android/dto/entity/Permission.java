package com.patis.wms.android.dto.entity;


import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Permission implements Serializable{

    private long id;
    private String name;

}
