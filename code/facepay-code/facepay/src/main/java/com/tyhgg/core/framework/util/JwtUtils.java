package com.tyhgg.core.framework.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
	private static String JWT_SECRET = "5838DF7FC3A34E26A61C034D5EC8541R";
	
	/**
	 * 生成jwt的token
	 * @方法名: generateToken
	 * @方法描述: 
	 * @param claims 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
	 * @param secret 明文密钥需要再经过aes加密后成为aes的正式密钥
	 * @param expiration 失效时间-分钟
	 * @return
	 * @return String
	 */
    public static String generateToken(Map<String, Object> claims, int expiration) {
    	
    	JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(expiration))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET);
    	
    	return builder.compact();
                
    }
	
    private static Date generateExpirationDate(int expiration) {
        return new Date(System.currentTimeMillis() + expiration * 60 * 1000);
    }

	/**
	 * 
	 * @方法名: createJWTJson
	 * @方法描述: 
	 * @param claims
	 * @param secret
	 * @param expiration
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String createJWTJson(Map<String, Object> claims,
			int expiration, String jsonStr) throws Exception {
		// 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。

//        SecretKey key = generalKey();
        
    	JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
//              .setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
//              .setIssuedAt(now)           //iat: jwt的签发时间
//    			.signWith(SignatureAlgorithm.HS512, key)//设置签名使用的签名算法和签名使用的秘钥
                .setExpiration(generateExpirationDate(expiration))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET);
    	
    	builder.setSubject(jsonStr);
    	
    	return builder.compact();
    	
    }

	/**
     * 解密jwtStr
     * @param jwtStr
     * @return
     * @throws Exception
     */
    public static Claims getClaimsFromToken(String jwtStr) {
//        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims;
        try {
	        claims = Jwts.parser()  //得到DefaultJwtParser
	           .setSigningKey(JWT_SECRET)         //设置签名的秘钥
	           .parseClaimsJws(jwtStr).getBody();
        } catch (Exception e) {
            LOGGER.error("", e);
            claims = null;
        }
        return claims;
    }
    

    public String refreshToken(String token, int expiration) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(JWT_SECRET, new Date());
            refreshedToken = generateToken(claims, expiration);
        } catch (Exception e) {
            LOGGER.error("", e);
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
    	//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        try {
        	LOGGER.info(new String(encodedKey, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("", e);
		}
        LOGGER.info(Base64.encodeBase64URLSafeString(encodedKey));
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        
        return key;
    }

    public static void main(String[] args) throws Exception {
    	
    	Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", "zhangsan");
        claims.put("roleId", "1");
        claims.put("orgId", "00011");
        claims.put("created", new Date());
    	
        int expiration = 30;
        
    	String token = generateToken(claims, expiration);
    	
    	System.out.println("jwt token = " + token);		
    			
        String jwtStr = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlSWQiOiIxIiwiY3JlYXRlZCI6MTU2MjEzOTA2MTc1MSwiZXhwIjoxNTYyMTQwODYxLCJ1c2VySWQiOiJhZG1pbiIsIm9yZ0lkIjoiYWRtaW4ifQ.W-My8g8_R3XxCL0gilvoCjI0e3eKGbHGKYEtWnN8_Fj2GyoqxGGavpIxV6trTKVRmlhhz5s_qSjQWCl25Z9EPg";
        Claims cl = JwtUtils.getClaimsFromToken(jwtStr);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        
        System.out.println(cl);
        
//        System.out.println(c.getId());//jwt
//        System.out.println(c.getIssuedAt());//Mon Feb 05 20:50:49 CST 2018
//        System.out.println(c.getSubject());//{id:100,name:xiaohong}
//        System.out.println(c.getIssuer());//null
//        System.out.println(c.get("uid", String.class));//DSSFAWDWADAS...
    }
}
