package com.patis.wms.android.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class PersonDTO {

    private long id;
    private String name;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String email;

}
