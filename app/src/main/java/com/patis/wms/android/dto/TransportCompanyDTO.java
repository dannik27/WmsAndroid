package com.patis.wms.android.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransportCompanyDTO {

    private long id;
    private CompanyDTO company;


    @Override
    public String toString() {
        return company.getName();
    }
}
