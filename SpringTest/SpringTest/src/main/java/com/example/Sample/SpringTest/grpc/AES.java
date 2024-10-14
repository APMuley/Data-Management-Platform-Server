package com.example.Sample.SpringTest.grpc;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class AES {

    static String decrypt(String ctB64, String keyString) throws Exception {

        byte[] key = keyString.getBytes("UTF-8");

        // Decode
        byte[] ivAndCt = Base64.getDecoder().decode(ctB64);

        // Extract the IV
        byte[] iv = new byte[16];
        System.arraycopy(ivAndCt, 0, iv, 0, iv.length);
        byte[] ct = new byte[ivAndCt.length - iv.length];
        System.arraycopy(ivAndCt, iv.length, ct, 0, ct.length);

        // Set up the AES cipher for decryption (AES/CBC/PKCS5Padding)
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Decrypt and return the result
        byte[] decryptedBytes = cipher.doFinal(ct);
        return new String(decryptedBytes, "UTF-8");
    }

    public static String encrypt(String plaintext, String keyString) throws Exception {

        // Convert key to bytes
        byte[] key = keyString.getBytes("UTF-8");

        // Set up the AES cipher for encryption (AES/CBC/PKCS5Padding)
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        // Generate IV
        byte[] iv = new byte[16]; // 16 bytes IV for AES
        new java.security.SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Encrypt the plaintext
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

        // Combine IV and ciphertext
        byte[] ivAndCiphertext = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, ivAndCiphertext, 0, iv.length);
        System.arraycopy(ciphertext, 0, ivAndCiphertext, iv.length, ciphertext.length);

        // Base64 encode the combined IV and ciphertext
        String base64EncodedCiphertext = Base64.getEncoder().encodeToString(ivAndCiphertext);

        return base64EncodedCiphertext;
    }

}


