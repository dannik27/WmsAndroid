package com.patis.wms.android.dto.create;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonCreateDTO {

    private long id;
    private String name;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String email;



}
