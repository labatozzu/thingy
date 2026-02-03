package com.myproject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Run once to generate bcrypt hash for data.sql: mvn exec:java -Dexec.mainClass="com.myproject.BcryptHashGenerator" */
public class BcryptHashGenerator {

    public static void main(String[] args) {
        String hash = new BCryptPasswordEncoder().encode("password123");
        System.out.println(hash);
    }
}
