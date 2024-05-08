package com.mchen2.myapp.test;

import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLOutput;
import java.util.Base64;

public class DESTest {

    @Test
    public void testDES() throws Exception {
        // Key密钥需要8字节
        String key = "A$%^&*()";
        String str = "HELLO WORLD, 你好啊";
        String encrypt = DESUtils.encrypt(str, key);
        System.out.println(encrypt);
        System.out.println(DESUtils.decrypt(encrypt, key));
    }

    public static class DESUtils {

        private static final String DES_ALGORITHM = "DES";

        public static String encrypt(String data, String key) throws GeneralSecurityException {
            // 根据密钥生成密钥规范
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            // 根据密钥规范生成密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            // 根据密钥工厂和密钥规范生成密钥
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            // 根据加密算法获取加密器
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            // 初始化加密器，设置加密模式和密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 加密数据
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            // 对加密后的数据进行Base64编码
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * DES解密
         *
         * @param encryptedData 加密后的数据，使用Base64编码
         * @param key           密钥，长度必须为8位
         * @return 解密后的数据
         */
        public static String decrypt(String encryptedData, String key) throws GeneralSecurityException {
            // 根据密钥生成密钥规范
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            // 根据密钥规范生成密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            // 根据密钥工厂和密钥规范生成密钥
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            // 对加密后的数据进行Base64解码
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            // 根据加密算法获取解密器
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            // 初始化解密器，设置解密模式和密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 解密数据
            byte[] decryptedData = cipher.doFinal(decodedData);
            // 将解密后的数据转换为字符串
            return new String(decryptedData);
        }
    }

}
