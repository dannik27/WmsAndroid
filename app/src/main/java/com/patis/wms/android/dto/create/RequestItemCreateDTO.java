package com.patis.wms.android.dto.create;


import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestItemCreateDTO implements Serializable {

    private long id;
    private int count;
    private long id_product;

    public RequestItemCreateDTO(int count, long id_product) {
        this.count = count;
        this.id_product = id_product;
    }
}
