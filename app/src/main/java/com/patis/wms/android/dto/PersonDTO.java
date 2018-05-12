package com.patis.wms.android.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class PersonDTO implements Serializable {

    private long id;
    private String name;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String email;


    public String getFio(){
        return String.format("%s %s. %s.", lastName, name.charAt(0), middleName.charAt(0));
    }

}
