package com.lisi4ka.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordConverter {
    public static String convert(String pswd) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(StandardCharsets.UTF_8.encode(pswd));
        }catch (NoSuchAlgorithmException ignored){
        }
        assert md != null;
        return String.format("%032x", new BigInteger(1, md.digest()));
    }
}