package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    private static final int MALE = 1;
    private static final int FEMALE = 0;
    private Integer uid;
    private String username;
    private String password;
    private int sex;
    private String telephone;
}
