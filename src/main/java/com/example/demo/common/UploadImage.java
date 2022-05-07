package com.example.demo.common;


import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/*
 *上传图片所用到的工具类
 * */
public class UploadImage {

    // 图片上传的位置
    static String BASE_PATH= "C:/resource/img";

    public static String upload(MultipartFile file) {
        // 获取上传图片的名称
        String filename = file.getOriginalFilename();
        // 为了保证图片在服务器中名字的唯一性，使用UUID来对filename进行改写
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + '-' + filename;
        // 创建一个文件实例对象
        File image = new File(BASE_PATH, newFileName);
        // 对这个文件进行上传操作
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }
}