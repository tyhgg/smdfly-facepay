//package com.tyhgg.core.framework.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.tyhgg.asr.system.entity.PeopleEntity;
//
///**
// * Jwt帮助类
// * @类名称: JwtHelper
// * @类描述: 
// * @创建人：zyt5668
// * @修改人：zyt5668
// * @修改时间：2019年7月1日 上午9:59:38
// * @修改备注：
// */
//@Component
//public class JwtHelper implements Serializable {
//	private static final long serialVersionUID = 5904455638335909521L;
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);
//	
//	private static final String CLAIM_KEY_USERNAME = "sub";
//    private static final String CLAIM_KEY_CREATED = "created";
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
//    public String getUserIdFromToken(String token) {
//        String userId;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            System.out.println("the claim is"+claims.toString());
//            userId = claims.getSubject();
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            userId = null;
//        }
//        return userId;
//    }
//
//    public Date getCreatedDateFromToken(String token) {
//        Date created;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            created = null;
//        }
//        return created;
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        Date expiration;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            expiration = claims.getExpiration();
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            expiration = null;
//        }
//        return expiration;
//    }
//
//    private Claims getClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            claims = null;
//        }
//        return claims;
//    }
//
//    private Date generateExpirationDate() {
//        return new Date(System.currentTimeMillis() + expiration * 1000);
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//        return (lastPasswordReset != null && created.before(lastPasswordReset));
//    }
//
//    public String generateToken(PeopleEntity peopleEntity) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_USERNAME, peopleEntity.getUserId());
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims);
//    }
//
//    private String generateToken(Map<String, Object> claims) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpirationDate())
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = getCreatedDateFromToken(token);
//        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                && !isTokenExpired(token);
//    }
//
//    public String refreshToken(String token) {
//        String refreshedToken;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims);
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }
//
//    public Boolean validateToken(String token, PeopleEntity peopleEntity) {
//        final String userId = getUserIdFromToken(token);
//        // 获取token的创建时间
////        final Date created = getCreatedDateFromToken(token);
//        return (
//        		userId.equals(peopleEntity.getUserId())
//                        && !isTokenExpired(token));
//        // 有些安全性要求高的系统还要判断最后一次修改密码的时间,本系统就不控制了
//        // && !isCreatedBeforeLastPasswordReset(created, peopleEntity.getLastpwdupdated())
//        
//    }
//
////
////    public static void main(String[] args){
////
////        try {
////            String a=DigestUtils.md5DigestAsHex("111111".getBytes("utf-8"));
////            System.out.println(a);
////
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
////    }
//}
