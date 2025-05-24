package com.WhiteDeer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class RedisSerializationUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将对象序列化为JSON字符串，处理Blob字段
    public static String serializeObject(Object object) throws JsonProcessingException {
        // 创建副本避免修改原始对象
        ObjectNode node = objectMapper.valueToTree(object);

        // 查找并处理所有Blob字段
        processBlobFields(node);

        return objectMapper.writeValueAsString(node);
    }

    // 反序列化JSON字符串为对象
    public static <T> T deserializeObject(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectNode node = (ObjectNode) objectMapper.readTree(json);

        // 查找并还原所有Blob字段
        processBase64Fields(node);

        return objectMapper.treeToValue(node, clazz);
    }

    // 递归处理JSON节点中的Blob字段
    private static void processBlobFields(JsonNode node) {
        if (node.isObject()) {
            ((ObjectNode) node).fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();

                // 如果字段是Blob类型，将其转换为Base64
                if (value.isNull() || value.isObject()) {
                    try {
                        // 假设Blob字段名为"face"，根据实际情况调整
                        if ("face".equals(fieldName) && value.isObject()) {
                            // 获取Blob字节并转换为Base64
                            String base64 = blobToBase64((Blob) value);
                            ((ObjectNode) node).put(fieldName, base64);
                        } else {
                            processBlobFields(value); // 递归处理子节点
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("处理Blob字段失败", e);
                    }
                }
            });
        } else if (node.isArray()) {
            ((ArrayNode) node).elements().forEachRemaining(RedisSerializationUtils::processBlobFields);
        }
    }

    // 将Base64字符串还原为Blob
    private static void processBase64Fields(JsonNode node) {
        if (node.isObject()) {
            ((ObjectNode) node).fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();

                if (value.isTextual() && "face".equals(fieldName)) {
                    try {
                        // 将Base64转换回Blob
                        Blob blob = base64ToBlob(value.asText());
                        ((ObjectNode) node).putPOJO(fieldName, blob);
                    } catch (Exception e) {
                        throw new RuntimeException("还原Blob字段失败", e);
                    }
                } else {
                    processBase64Fields(value); // 递归处理子节点
                }
            });
        } else if (node.isArray()) {
            ((ArrayNode) node).elements().forEachRemaining(RedisSerializationUtils::processBase64Fields);
        }
    }

    // 将Blob转换为Base64字符串
    private static String blobToBase64(Blob blob) throws SQLException, IOException {
        try (InputStream inputStream = blob.getBinaryStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }

    // 将Base64字符串转换为Blob
    private static Blob base64ToBlob(String base64) throws SQLException {
        if (base64 == null || base64.isEmpty()) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new SerialBlob(bytes);
    }
}
