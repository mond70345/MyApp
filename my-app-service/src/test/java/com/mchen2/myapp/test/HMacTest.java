package com.mchen2.myapp.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HMacTest {

    @Test
    public void testHMac() throws NoSuchAlgorithmException, InvalidKeyException {
        // 待加密的消息
        String message = "Hello, World!";
        // 密钥
        String key = "secretKey";

        // 创建 HMAC-SHA256 密钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");

        // 创建 HMAC-SHA256 实例
        Mac mac = Mac.getInstance("HmacSHA256");
        // 初始化 HMAC-SHA256 实例
        mac.init(secretKeySpec);

        // 计算 HMAC 值
        byte[] hmac = mac.doFinal(message.getBytes());

        // 输出 HMAC 值
        System.out.println("HMAC 值: " + bytesToHexString(hmac));


        HMac hmac1 = SecureUtil.hmac(HmacAlgorithm.HmacSHA256, key);
        String digested = hmac1.digestHex(message);
        System.out.println("HMAC 值: " + digested);
        System.out.println(StrUtil.utf8Bytes(key));

    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }



}
