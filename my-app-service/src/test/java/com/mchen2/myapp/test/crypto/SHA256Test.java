package com.mchen2.myapp.test.crypto;

import cn.hutool.core.codec.Base16Codec;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Test {

    @Test
    public void testSHA256() {
        String str = "12345";

        testWithHutool(str);

        testWithJava(str);
        System.out.println("{bcrypt}$2a$10$1mVnUpvhYaooe.SD966oS.p7CVe/drmfnBx9o9WXNTlRwjSC4RD3y".length());
    }

    private void testWithHutool(String input) {
        String encoded = SecureUtil.sha256(input);
        System.out.println("SHA256加密后：" + encoded);
    }

    private void testWithJava(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            System.out.println("SHA256加密后：" + new String(Base16Codec.CODEC_LOWER.encode(digest)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static class SHA256 {
        private static final String SHA_256_ALGORITHM = "SHA-256";
        public static String encrypt(String data) throws Exception {
            //获取SHA-256算法实例
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            //计算散列值
            byte[] digest = messageDigest.digest(data.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            //将byte数组转换为15进制字符串
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return stringBuilder.toString();
        }

        public static void main(String[] args) throws Exception {
            String data = "Hello World";
            String encryptedData = encrypt(data);
            System.out.println("加密后的数据：" + encryptedData);
        }
    }



    public static class SHA1Utils {

        public static String getSHA1(String str) {
            return SHA1Utils.bytesToHexString(SHA1Utils.computeSHA1(str.getBytes()));
        }
        public static byte[] computeSHA1(byte[] content) {
            try {
                MessageDigest sha1 = MessageDigest.getInstance("SHA1");
                return sha1.digest(content);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
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
