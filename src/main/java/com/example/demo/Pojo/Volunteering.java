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
    private String volName;
    private Date startTime;
    private Date endTime;
    // 报名该活动的用户
    private List<User> userList;
}
