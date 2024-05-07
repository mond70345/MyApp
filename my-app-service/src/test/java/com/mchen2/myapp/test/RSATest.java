package com.mchen2.myapp.test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSATest {

    @Test
    public void testRSA() throws Exception {
        String str = "拒绝日期";


        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        String privateKeyStr = bytesToBase64(privateKey.getEncoded());
        System.out.println("私钥：" + privateKeyStr);
        //获得公钥
        String publicKeyStr = bytesToBase64(publicKey.getEncoded());
        System.out.println("公钥：" + publicKeyStr);

        RSA rsa = new RSA(privateKeyStr, publicKeyStr);
        System.out.println(rsa);

        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.out.println("公钥加密：" + bytesToBase64(encrypt));

        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("私钥解密：" + new String(decrypt, StandardCharsets.UTF_8));

//        //私钥加密，公钥解密
//        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(text, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
//        System.out.println("私钥加密：" + bytesToBase64(encrypt2));
//        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
//        System.out.println("公钥解密：" + bytesToBase64(decrypt2));

        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKeyStr, publicKeyStr);
        //签名
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        byte[] signed = sign.sign(data);
        String signedStr = bytesToBase64(signed);
        System.out.println("签名：" + signedStr);

        //验证签名
        boolean verify = sign.verify(data, base64ToBytes(signedStr));
        System.out.println("验签：" + verify);

    }


    /**
     * 字节数组转Base64编码
     *
     * @param bytes 字节数组
     * @return Base64编码
     */
    private static String bytesToBase64(byte[] bytes) {
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Base64编码转字节数组
     *
     * @param base64Str Base64编码
     * @return 字节数组
     */
    private static byte[] base64ToBytes(String base64Str) {
        byte[] bytes = base64Str.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(bytes);
    }

    public static class RSADemo {
        public RSADemo() {
        }

        PublicKey pbkey;
        PrivateKey prkey;

        public void generateKey() {
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(1024);
                KeyPair kp = kpg.genKeyPair();
                pbkey = kp.getPublic();
                prkey = kp.getPrivate();
            } catch (Exception e) {
            }
        }

        //加密，需要公钥
        public byte[] encrypt(byte[] ptext) throws Exception {
            // 获取公钥及参数e,n
            RSAPublicKey pbk = (RSAPublicKey) pbkey;
            BigInteger e = pbk.getPublicExponent();
            BigInteger n = pbk.getModulus();
            // 获取明文m
            BigInteger m = new BigInteger(ptext);
            // 计算密文c
            BigInteger c = m.modPow(e, n);
            return c.toByteArray();
        }

        //使用私钥进行解密
        public byte[] decrypt(byte[] ctext) throws Exception {
            // 读取密文
            BigInteger c = new BigInteger(ctext);
            // 读取私钥
            RSAPrivateKey prk = (RSAPrivateKey) prkey;
            BigInteger d = prk.getPrivateExponent();

            // 获取私钥参数及解密
            BigInteger n = prk.getModulus();
            BigInteger m = c.modPow(d, n);

            // 显示解密结果
            byte[] mt = m.toByteArray();
            return mt;
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
