package com.WhiteDeer.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;

public class BlobConverter {
    public static String blobToBase64(Blob blob) throws IOException {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            return null;
        }
    }
}
