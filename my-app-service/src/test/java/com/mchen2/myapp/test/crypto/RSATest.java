package com.mchen2.myapp.test.crypto;

import cn.hutool.core.codec.Base16Codec;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSATest {

    @Test
    public void test() throws Exception {
        KeyPair keyPair = RSAUtils.generateKeyPair();
        String encrypt = RSAUtils.encrypt("AAAASSSDFSDF", keyPair.getPublic());
        System.out.println(encrypt);
        System.out.println(RSAUtils.decrypt(encrypt, keyPair.getPrivate()));
    }

    public static class RSAUtils {
        private static final String RSA_ALGORITHM = "RSA";

        /**
         * 生成RSA密钥对
         *
         * @return RSA密钥对
         */
        public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(1024); // 密钥大小为1024位
            return keyPairGenerator.generateKeyPair();
        }

        /**
         * 使用公钥加密数据
         *
         * @param data      待加密的数据
         * @param publicKey 公钥
         * @return 加密后的数据
         */
        public static String encrypt(String data, PublicKey publicKey) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            System.out.println(Base16Codec.CODEC_LOWER.encode(encryptedData));
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * 使用私钥解密数据
         *
         * @param encryptedData 加密后的数据
         * @param privateKey    私钥
         * @return 解密后的数据
         */
        public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        }


    }


}
