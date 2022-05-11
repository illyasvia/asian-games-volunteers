package com.example.demo.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    /*Location*/
    //西湖区
    private static final int XIHU = 1;
    //上城区
    private static final int SHANGCHENG = 2;
    //下城区
    private static final int XIACHENG = 3;
    //江干区
    private static final int JIANGGAN = 4;
    //滨江区
    private static final int BINGJIANG = 5;
    //富阳区
    private static final int FUYANG = 6;

    /*Status*/
    // 正在招募
    private static final int ACTIVE = 1;
    // 即将进行
    private static final int WAIT = 2;
    // 活动截止
    private static final int CLOSED = 3;
    // 活动结束
    private static final int END = 4;


    /*Type*/
    //知识类型活动
    private static final int KNOWLEDGE = 1;
    //环保类型活动
    private static final int ENVIRONMENTAL = 2;
    //社会类型活动
    private static final int SOCIETY = 3;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end;
    // 报名该活动的用户
    private List<User> userList;
}
