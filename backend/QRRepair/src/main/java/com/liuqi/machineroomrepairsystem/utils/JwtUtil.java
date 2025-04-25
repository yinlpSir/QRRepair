package com.liuqi.machineroomrepairsystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 *  JWT 工具类
 *  TODO: 以下用的是对称加密方式.相对应的还有非对称加密
 *
 *  java-jwt github address : https://github.com/auth0/java-jwt
 *  参考文章：https://www.cnblogs.com/ccx-lly/p/17104192.html
 */
@Data // 它对静态属性无效
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    private static String tokenSecret; // jwt加密的秘钥
    private static long tokenExpire;// jwt有效时间

    public void setTokenExpire(long tokenExpire) {
        JwtUtil.tokenExpire = tokenExpire;
    }

    public void setTokenSecret(String tokenSecret) {
        JwtUtil.tokenSecret = tokenSecret;
    }

    /**
     *  生成 json web token
     * @param username
     * @return token
     * @throws JWTCreationException 无法创建 JWT 时引发的异常。它有个子异常是 SignatureGenerationException (无法生成签名时引发的异常)
     */
    public static String generateToken(String username) throws JWTCreationException {
        Instant now = Instant.now();
        //设置头、负载、签名 （头可以不设置，有默认值）
        return JWT.create()//创建 jwt
                .withClaim("iss","lp")
                .withClaim("username", username)// payload
                .withExpiresAt(now.plusSeconds(tokenExpire))// jwt的有效期
                .sign(Algorithm.HMAC512(tokenSecret));// 设置 签名。用的算法是 HMAC512
    }

    /**
     *  验证token
     * 关于 java-jwt 中的 所有异常，请参见：https://javadoc.io/doc/com.auth0/java-jwt/latest/com/auth0/jwt/exceptions/package-summary.html
     * @param token
     * @return username
     * @throws JWTVerificationException 验证 JWT 时引发的所有异常的父级。
     */
    public static Claim parseToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(tokenSecret)).build();//获取jwt校验器
        DecodedJWT jwt = verifier.verify(token);//验证token
        Claim username = jwt.getClaim("username");//获取token负载的id属性值
        //jwt.getExpiresAt()); // 获取过期时间
        return username;
    }
}
