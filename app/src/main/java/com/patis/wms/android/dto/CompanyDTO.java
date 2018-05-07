package com.patis.wms.android.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CompanyDTO {

  private long id;
  private String name;
  private String description;
  private String inn;
  private String kpp;
  private String okpo;
  private String email;
  private String phone;

  private PersonDTO contactPerson;




}
