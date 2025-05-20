package com.WhiteDeer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;


public class TempFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(TempFileUtils.class);
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static File convertToTempImage(String base64Data) throws IOException {

        String format = "jpg";

        // 移除Base64前缀（如果存在）
        if (base64Data.startsWith("data:image")) {
            base64Data = base64Data.substring(base64Data.indexOf(',') + 1);
        }

        // 解码Base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);

        // 创建临时文件
        String fileName = "temp_image_" + UUID.randomUUID() + "." + format.toLowerCase();
        File tempFile = new File(TEMP_DIR, fileName);

        // 写入文件
        Files.write(tempFile.toPath(), imageBytes);
        logger.info("Created temp image file: {}", tempFile.getAbsolutePath());

        return tempFile;
    }
}
