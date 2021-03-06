package com.patis.wms.android.dto;


import com.patis.wms.android.dto.entity.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkerDTO implements Serializable {

    private long id;
    private PersonDTO person;
    private Role role;
    private Date dateHired;
    private Date dateFired;


}
