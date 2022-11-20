package com.example.back_end.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 主要是在其加密的过程中作为算法的一个参数，这个值的复杂度影响到了数据传输和存储时的复杂度
 */
public class JwtUtil {

    //有效期
    public static final Long JWT_TTL = 60 * 60 * 1000L;
    // 设置秘钥明文
    public static final String JWT_KEY = "itlils";


    /**
     * 生成jwt
     * @param subject token中要存放的数据(json格式)
     * @return
     */
    public static String createJWT(String subject){
        System.out.println("subject" + subject);
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成jwt
     * @param subject
     * @param ttlMillis token超过时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();

    }
    //获取随机序列
    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-","");
        return token;
    }

    // 创建token的方法
    public static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        // 设置签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取加密算法
        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //判断存货时间是否设定，没有设定选用默认值
        if(ttlMillis == null) {
            ttlMillis=JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        //获取过期时间
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)        //唯一的ID
                .setSubject(subject)        // 主题  可以是JSON数据
                .setIssuer("ydlclass")      // 签发者
                .setIssuedAt(now)        // 签发时间
                .signWith(signatureAlgorithm, secretKey)    // 使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey，
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
