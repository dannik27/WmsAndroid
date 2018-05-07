package com.patis.wms.android.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyCreateDTO {




  private long id;
  private String name;
  private String description;
  private String inn;
  private String kpp;
  private String okpo;
  private String email;
  private String phone;

  private long id_person;




}
