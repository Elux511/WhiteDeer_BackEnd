package com.WhiteDeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class main {
    public static void main(String[] args) {
        SpringApplication.run(main.class, args);
    }
}
//人脸识别测试
//    public static void main(String[] args) {
//        PyAPI.trainFaceLabels(String.valueOf({id}), {path});//魔法路径
//        boolean a = PyAPI.faceRecognition(String.valueOf({id}),{path});//魔法路径
//        System.out.println(a);
//    }

//定位测试
//public static void main(String[] args) {
//    System.out.println(PyAPI.geoFencing());
//}