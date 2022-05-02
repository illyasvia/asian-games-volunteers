package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Volunteering {
    private Integer vid;
    private String volName;
    private Date startTime;
    private Date endTime;

}
