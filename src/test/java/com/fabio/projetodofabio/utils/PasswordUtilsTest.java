package com.fabio.projetodofabio.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {

    private static final String SENHA = "123456";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() throws Exception{
        Assertions.assertNull(PasswordUtils.generateBCrypt(null));
    }

    @Test
    public void testGenerateHash() throws Exception{
        String hash = PasswordUtils.generateBCrypt(SENHA);
        Assertions.assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
    }
}
