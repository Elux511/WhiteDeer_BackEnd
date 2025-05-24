//package com.WhiteDeer.util;
//
//
//import java.io.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class PyAPI {
//    private static final Logger logger = LoggerFactory.getLogger(PyAPI.class);
//
//    //获得用户人脸的特征（用于初次记录）
//    //传入用户人脸图片，保存人脸特征
//    static public boolean trainFaceLabels(String user_id, String img_base64) {
//        //创建临时图片文件
//        File tempFile = null;
//        try {
//            tempFile = TempFileUtils.convertToTempImage(img_base64);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//
//            //指定使用的python解释器，python文件路径和传入的参数
//            ProcessBuilder pb = new ProcessBuilder("python", "trainFaceLabels.py",user_id,tempFile.getAbsolutePath());
//            pb.directory(new File("src/main/resources/pyAPI"));
//            //合并标准错误流到输出
//            pb.redirectErrorStream(true);
//            //运行
//            Process p = pb.start();
//
//            int exit_code = p.waitFor();
//
//            InputStream in = p.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String line = reader.readLine();
//
//            return "true".equals(line);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return false;
//        }finally {
//            // 删除临时文件
//            if (tempFile.exists() && !tempFile.delete()) {
//                logger.warn("Failed to delete temp file: {}", tempFile.getAbsolutePath());
//            }
//        }
//    }
//
//    //人脸识别（用于后续打卡）
//    //将传入的人脸图片与训练模型中的图片一一对照
//    static public boolean faceRecognition(String user_id, String img_base64) {
//        //创建临时图片文件
//        File tempFile = null;
//        try {
//            tempFile = TempFileUtils.convertToTempImage(img_base64);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        try {
//            //指定使用的python解释器，python文件路径和传入的参数
//            ProcessBuilder pb = new ProcessBuilder("python", "faceRecognition.py",user_id,tempFile.getAbsolutePath());
//            pb.directory(new File("src/main/resources/pyAPI"));
//            //合并标准错误流到输出
//            pb.redirectErrorStream(true);
//            //运行
//            Process p = pb.start();
//
//            int exit_code = p.waitFor();
//
//            //读取输出
//            InputStream in = p.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String line = reader.readLine();
//            //根据py文件输出返回bool
//            return "True".equals(line);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
package com.WhiteDeer.util;

import java.io.*;
import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PyAPI {
    private static final Logger logger = LoggerFactory.getLogger(PyAPI.class);
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    // 获得用户人脸的特征（用于初次记录）
    // 传入用户人脸图片，保存人脸特征
    static public CompletableFuture<Boolean> trainFaceLabelsAsync(String user_id, String img_base64) {
        return CompletableFuture.supplyAsync(() -> {
            // 创建临时图片文件
            File tempFile = null;
            try {
                tempFile = TempFileUtils.convertToTempImage(img_base64);
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            Process process = null;
            try {
                // 指定使用的python解释器，python文件路径和传入的参数
                ProcessBuilder pb = new ProcessBuilder("python", "trainFaceLabels.py", user_id, tempFile.getAbsolutePath());
                pb.directory(new File("src/main/resources/pyAPI"));
                // 合并标准错误流到输出
                pb.redirectErrorStream(true);
                // 运行
                process = pb.start();

                // 异步处理输出流
                String output = readOutput(process.getInputStream());
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    throw new IOException("Python process failed with exit code " + exitCode + ": " + output);
                }

                return "true".equals(output.trim());
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException("Face training failed", e);
            } finally {
                // 删除临时文件
                if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
                    logger.warn("Failed to delete temp file: {}", tempFile.getAbsolutePath());
                }
                // 确保进程被销毁
                if (process != null) {
                    process.destroy();
                }
            }
        }, executor);
    }

    // 人脸识别（用于后续打卡）
    // 将传入的人脸图片与训练模型中的图片一一对照
    static public CompletableFuture<Boolean> faceRecognitionAsync(String user_id, String img_base64) {
        return CompletableFuture.supplyAsync(() -> {
            // 创建临时图片文件
            File tempFile = null;
            try {
                tempFile = TempFileUtils.convertToTempImage(img_base64);
            } catch (IOException e) {
                throw new CompletionException(e);
            }

            Process process = null;
            try {
                // 指定使用的python解释器，python文件路径和传入的参数
                ProcessBuilder pb = new ProcessBuilder("python", "faceRecognition.py", user_id, tempFile.getAbsolutePath());
                pb.directory(new File("src/main/resources/pyAPI"));
                // 合并标准错误流到输出
                pb.redirectErrorStream(true);
                // 运行
                process = pb.start();

                // 异步处理输出流
                String output = readOutput(process.getInputStream());
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    throw new IOException("Python process failed with exit code " + exitCode + ": " + output);
                }

                return "True".equals(output.trim());
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException("Face recognition failed", e);
            } finally {
                // 删除临时文件
                if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
                    logger.warn("Failed to delete temp file: {}", tempFile.getAbsolutePath());
                }
                // 确保进程被销毁
                if (process != null) {
                    process.destroy();
                }
            }
        }, executor);
    }

    // 辅助方法：读取输入流内容
    private static String readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        }
    }

    // 关闭线程池
    public static void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}