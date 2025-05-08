package com.WhiteDeer;

import com.WhiteDeer.service.PyAPI;

public class Test {
    public static void main(String[] args) {
        PyAPI.trainFaceLabels(String.valueOf(2),"C:\\Code\\test2.jpg");//魔法路径
        boolean a = PyAPI.faceRecognition(String.valueOf(2),"C:\\Code\\test1.jpg");//魔法路径
        System.out.println(a);
    }
}