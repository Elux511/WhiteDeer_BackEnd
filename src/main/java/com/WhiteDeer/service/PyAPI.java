package com.WhiteDeer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class PyAPI {
    static public boolean faceRecognition(String user_id, String img_path){
        try {
            //指定使用的python解释器，python文件路径和传入的参数
            ProcessBuilder pb = new ProcessBuilder("python", "D:/for_pycharm/myApi/faceRecognition.py",user_id,img_path);
            //合并标准错误流到输出
            pb.redirectErrorStream(true);
            //运行
            Process p = pb.start();

            int exit_code = p.waitFor();

            //读取输出
            InputStream in = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            //根据py文件输出返回bool
            return "True".equals(line);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    static public Vector<Double> geoFencing(){
        try {
            //指定使用的python解释器，python文件路径和传入的参数
            ProcessBuilder pb = new ProcessBuilder("python", "D:/for_pycharm/myApi/geoFencing.py");//记得该路径
            //合并标准错误流到输出
            pb.redirectErrorStream(true);
            //运行
            Process p = pb.start();

            //读取输出
            InputStream in = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            Vector<Double> location = new Vector<>();
            while((line = reader.readLine()) != null){
                location.add(Double.parseDouble(line));
            }
            if(location.size() != 2){
                System.out.println("Python exited with code " + p.exitValue());
            }
            int exit = p.exitValue();
            if (exit != 0){
                System.out.println("Python exited with code " + exit);
            }
            //根据py文件输出返回位置
            return location;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
