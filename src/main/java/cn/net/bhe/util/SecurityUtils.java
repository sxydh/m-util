package cn.net.bhe.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {

    public static String encode(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
