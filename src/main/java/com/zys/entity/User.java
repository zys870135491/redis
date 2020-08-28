package com.zys.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable{

    private Long id;
    private String name;
    private Date birthDay;
    private Integer age;

}
