package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Volunteering {
    private Integer vid;
    private String title;
    private Integer exp;
    private Integer like;
    private String coverv;
    private Integer pNum;
    private Integer type;
    private Integer status;
    private String content;
    private Integer location;
    private Date start;
    private Date end;
    // 报名该活动的用户
    private List<User> userList;
}
