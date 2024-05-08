package com.mchen2.myapp.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class BcryptTest {

    @Test
    public void testBcrypt() {
        final String input = "123456";

        final MD5 md5 = MD5.create();
        String digested = md5.digestHex(input, StandardCharsets.UTF_8);
        System.out.println(BCrypt.gensalt());
        String hashpw = BCrypt.hashpw(Base64.encode(digested), BCrypt.gensalt());
        System.out.println("加密后："+hashpw);
    }
}
