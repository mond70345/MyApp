package com.mchen2.myapp.test.crypto;

import cn.hutool.core.codec.Base16Codec;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {

    @Test
    public void testMD5() {
        String str = "12345";

        // Hutool
        testWithHutool(str);

        // Java
        testWithJava(str);
    }

    private void testWithHutool(String input) {
        final MD5 md5 = MD5.create();
        String digested = md5.digestHex(input, StandardCharsets.UTF_8);
        System.out.println("MD5加密后：" + digested);
    }

    private void testWithJava(String input) {
        try {
            // 创建 MD5 消息摘要对象
            final MessageDigest md = MessageDigest.getInstance("MD5");
            // 对字符串进行加密
            byte[] encryptedBytes = md.digest(input.getBytes());
            System.out.println(encryptedBytes.length);

            // MD5 算法产生的结果是由 128 个二进制位组成的。为了方便表示和阅读，通常将这 128 位用 32 个十六进制数字来表示。每个十六进制数字代表 4 个二进制位。
            // 将加密后的字节数组转换为十六进制字符串
            System.out.println("MD5加密后：" + new String(Base16Codec.CODEC_LOWER.encode(encryptedBytes)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
