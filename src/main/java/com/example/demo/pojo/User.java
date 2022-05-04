package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    /*Sex*/
    private static final int MALE = 1;
    private static final int FEMALE = 0;

    private Integer uid;
    private String  username;
    private String  password;
    private Integer sex;
    private Integer exp;
    private Date    register;
    private String  idCard;
    private String  phone;
    private String  email;
    private String  profile;
    // 用户报名的活动
    private List<Volunteering> registrationList;
}
