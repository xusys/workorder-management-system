package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRATION = 24*60*60*1000; // 过期时间：24小时，单位：毫秒
    private static final String SECRET = "scut_work_order"; // 密钥盐

    /**
     * 参加token
     * @param user
     * @return
     */
    public static String createToken(User user){
        Date expiresAt = new Date(System.currentTimeMillis()+EXPIRATION);
        // 设置JWT头部，表明jwt使用的签名算法
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 创建token
        String token= JWT.create()
                .withHeader(map)       //设置头部信息Header
                .withIssuer("SERVICE") // 声明 签名由谁生成，例如：服务器
                .withClaim("username",user.getUsername()) // 自定义，用户名
                .withClaim("positionId",user.getPositionId()) // 自定义，职位id
                .withClaim("areaId",user.getAreaId()) // 自定义，地区id
                .withExpiresAt(expiresAt)  // 设置过期时间
                .sign(Algorithm.HMAC256(SECRET));  // 设置签名解码算法
        return token;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token){
        JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("SERVICE").build();
        return verifier.verify(token);
    }
}
