package com.mchen2.myapp.test;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Test {

    @Test
    public void testSHA1() {
        String str = "123456";

        String sha11 = SHA1Utils.getSHA1(str);
        System.out.println(sha11);

        String sha12 = SecureUtil.sha1(str);
        System.out.println(sha12);


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
