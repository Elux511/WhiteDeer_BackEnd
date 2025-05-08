package com.WhiteDeer;

import com.WhiteDeer.service.PyAPI;

public class Test {
    public static void main(String[] args) {
        PyAPI.trainFaceLabels(String.valueOf(1),"C:/Users/19877/Documents/Tencent Files/1987794678/FileRecv/MobileFile/Screenshot_2025_0305_194418.png");
        boolean a = PyAPI.faceRecognition(String.valueOf(1),"C:/Users/19877/Documents/Tencent Files/1987794678/FileRecv/MobileFile/Screenshot_2025_0305_194418.png");
        System.out.println(a);
    }
}