package com.internet.shop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.internet.shop.exceptions.HashingFailedException;
import org.apache.log4j.Logger;

public class HashUtil {
    private static final String HASHING_ALGORITHM = "SHA-512";
    private static Logger logger = Logger.getLogger(HashUtil.class);

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b: digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
            throw new HashingFailedException("Failed to hash password", e);
        }
        return hashedPassword.toString();
    }
}
