package com.mchen2.myapp.test;

import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {

    @Test
    public void testMD5() {
        String str = "123456";

        MD5 md5 = MD5.create();
        String digested = md5.digestHex(str, StandardCharsets.UTF_8);
        System.out.println(digested);

        String digested2 = MD5Utils.getMD5(str);
        System.out.println(digested2);
    }

    public static class MD5Utils {
        public static String getMD5(String str) {
            try {
                // 创建 MD5 消息摘要对象
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 对字符串进行加密
                byte[] encryptedBytes = md.digest(str.getBytes());
                // 将加密后的字节数组转换为十六进制字符串
                return bytesToHexString(encryptedBytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static String bytesToHexString(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
    }

}
