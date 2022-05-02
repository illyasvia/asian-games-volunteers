package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Volunteering implements Serializable {
    // 正在招募
    private static final int ACTIVE = 1;
    // 即将进行
    private static final int WAIT = 2;
    // 活动截止
    private static final int CLOSED = 3;
    // 活动结束
    private static final int END = 4;

    //知识型活动
    private static final int KNOWLEDGE = 1;
    //社区型活动
    private static final int COMMUNITY = 2;
    //环保型活动
    private static final int ENVIRONMENTAL = 3;


    private Integer vid;
    private String title;
    private Integer exp;
    private Integer like; // 点赞数
    private String cover;// 封面
    @JsonProperty("pNum")//这里必须加这个注释，不然怎么读取都是NULL
    private Integer pNum;// 限制人数
    private Integer type;
    private Integer status;
    private String content;
    private Integer location;
    private Date start;
    private Date end;
    // 报名该活动的用户
    private List<User> userList;
}
