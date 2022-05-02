package com.example.demo.pojo;

import com.example.demo.pojo.User;
import com.example.demo.pojo.Volunteering;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: Condition
 * Description:
 * date: 2022/5/2 23:43
 *
 * @author zyu
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condition implements Serializable {
    private Integer start;
    private Integer rows;
    private User user;
    private Volunteering volunteering;
}
