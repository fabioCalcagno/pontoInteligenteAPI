package com.fabio.projetodofabio.utils;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
public class PasswordUtils {

    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public static String generateBCrypt(String senha){
        if (senha == null){
            return senha;
        }
        log.info("Generating hash with BCrypt");
        return new BCryptPasswordEncoder().encode(senha);
    }
}
