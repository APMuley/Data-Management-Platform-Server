package com.example.Sample.SpringTest.grpc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Date;

public class JWTToken {
    private static final String SECRET = "django-insecure-r&4(o%=fvv$*d6)9qpojh6$q&ro)md=)ea$uwtm0kq_2_#4c88";
    private static final String ISSUER = "DATAMANAGEMENTPLATFORM";
    private static final long EXPIRATION_TIME = 3600 * 1000; // 1 hour in milliseconds

    // Method to create a token
    public static String createToken(String userID, String username) {
        try {
            // Header
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String encodedHeader = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(header.getBytes(StandardCharsets.UTF_8));

            // Payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("user_id", userID);
            payload.put("username", username);
            payload.put("role", "user");
            String payloadJson = new org.json.JSONObject(payload).toString();
            String encodedPayload = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));

            // Signature
            String signature = sign(encodedHeader + "." + encodedPayload, SECRET);

            // JWT token
            return encodedHeader + "." + encodedPayload + "." + signature;

        } catch (Exception e) {
            throw new RuntimeException("Error while creating token", e);
        }
    }

    // Method to verify a token
    public static boolean verifyToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                System.out.println("Invalid token format");
                return false;
            }

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            // Verify signature
            String expectedSignature = sign(header + "." + payload, SECRET);
            if (!expectedSignature.equals(signature)) {
                System.out.println("Invalid signature");
                return false;
            }

            // Decode payload
            String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
            org.json.JSONObject jsonObject = new org.json.JSONObject(decodedPayload);



            System.out.println("Token is valid!");
            System.out.println("Subject: " + jsonObject.getString("username"));
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    // Helper method to sign data with HMAC SHA-256
    private static String sign(String data, String secret) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

}
