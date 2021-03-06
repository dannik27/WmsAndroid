package com.patis.wms.android.dto.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Role implements Serializable{

    private long id;
    private String name;
    List<Permission> permissions;

}
