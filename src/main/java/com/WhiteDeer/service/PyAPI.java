package com.WhiteDeer.service;

import java.io.*;
import java.util.Vector;

public class PyAPI {
    //获得用户人脸的特征（用于初次记录）
    //传入用户人脸图片，保存人脸特征
    static public void trainFaceLabels(String user_id, String img_path) {
        try {
            //指定使用的python解释器，python文件路径和传入的参数
            ProcessBuilder pb = new ProcessBuilder("python", "trainFaceLabels.py",user_id,img_path);
            pb.directory(new File("src/main/resources/pyAPI"));
            //合并标准错误流到输出
            pb.redirectErrorStream(true);
            //运行
            Process p = pb.start();

            int exit_code = p.waitFor();

            return;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    //人脸识别（用于后续打卡）
    //将传入的人脸图片与训练模型中的图片一一对照
    static public boolean faceRecognition(String user_id, String img_path){
        try {
            //指定使用的python解释器，python文件路径和传入的参数
            ProcessBuilder pb = new ProcessBuilder("python", "faceRecognition.py",user_id,img_path);
            pb.directory(new File("src/main/resources/pyAPI"));
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

    //获取设备当前经纬度(腾讯api)
    //返回纬度和经度
    static public Vector<Double> geoFencing(){
        try {
            //指定使用的python解释器，python文件路径和传入的参数
            ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/pyAPI/geoFencing.py");//记得该路径
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
