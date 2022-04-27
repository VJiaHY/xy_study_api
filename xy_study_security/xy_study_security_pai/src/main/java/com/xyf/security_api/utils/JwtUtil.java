package com.xyf.security_api.utils;

import com.xyf.security_api.config.template.JwtConfigTemplate;
import com.xyf.security_api.domain.LoginUser;
import com.xyf.security_api.domain.sys.SysUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
//@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

   @Autowired
    private  JwtConfigTemplate jwtConfigTemplate;

    //Token的加密算法，默认使用HS256
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    /**
     * 根据用户名生成Token字符串
     * Claims中存储的信息：
     * - Id -> id(用户id)
     * - Username -> username(用户名)
     * - Password -> password(秘密)
     * - Nickname -> nickname(用户昵称)
     * - Telephone -> telephone(用户手机号)
     * - iat(ISSUED_AT) -> 签发时间
     * - exp(EXPIRATION) -> 过期时间
     * Token只存储用户的基本信息，其他信息通过数据库查询获取(例如：用户权限、用户详细资料)
     * @param loginUser 用于生成Token的用户信息
     * @return 要生成的Token字符串
     */
    public  String generateToken(LoginUser loginUser) {

        //用于存储Payload中的信息
        Map<String, Object> claims = new HashMap<>();
        SysUser sysUser = loginUser.getSysUser();
        String username = sysUser.getUserName();
        //设置有效载荷(Payload)
        claims.put("Id", sysUser.getId());
        claims.put("Username", username);
        claims.put("Password", sysUser.getPassword());
        claims.put("Nickname", sysUser.getNickName());
        claims.put("Phonenumber", sysUser.getPhonenumber());
        claims.put("Perms",loginUser.getPermsList());
        //签发时间
        claims.put(Claims.ISSUED_AT, new Date());
        //过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + jwtConfigTemplate.getExpire());
        claims.put(Claims.EXPIRATION, expirationDate);
        //使用JwtBuilder生成Token，其中需要设置Claims、过期时间，最后再
        String token = Jwts
                .builder()//获取DefaultJwtBuilder
                .setClaims(claims)//设置声明
                .signWith(signatureAlgorithm, jwtConfigTemplate.getSecret())//使用指定加密方式和密钥进行签名
                .compact();//生成字符串类型的Token

        //将生成的Token字符串存入Redis，同时设置缓存有效期
      /*  if(StringUtils.hasText(token)){
            tokenCache.opsForValue().set(username, token, jwtConfigTemplate.getExpire(), TimeUnit.MILLISECONDS);
        }*/
        return token;
    }

    /**
     * 验证Token
     * 验证方法：将客户端携带的Token进行解析，如果没有抛出解析异常(JwtException)，如果相同就返回true，反之返回false
     * @param token 客户端携带过来的要验证的Token
     * @return Token是否有效
     */
    public  boolean validateToken(String token) {
        try{
            if(isTokenExpired(token)){
                return false;
            }
        }catch(JwtException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断Token是否过期
     * 使用Token有效载荷中的过期时间与当前时间进行比较
     * @param token 要判断的Token字符串
     * @return 是否过期
     */
    private  boolean isTokenExpired(String token) throws JwtException{
        try{
            //从Token中获取有效载荷
            Claims claims = parseToken(token);
            //从有效载荷中获取用户名
            String username = claims.get("Username", String.class);
            if(StringUtils.isEmpty(username)){
                return true;
            }
            //通过用户名从缓存中获取指定的Token
          /*  Object cacheToken = tokenCache.opsForValue().get("Username");
            if(StringUtils.isEmpty(cacheToken)){
                return true;
            }*/
        }catch(SignatureException e){
            throw new SignatureException("令牌签名校验不通过！");
        }
        return false;
    }

    /**
     * 解析Token字符串并且从其中的有效载荷中获取指定Key的元素，获取的是Object类型的元素
     * @param token 解析哪个Token字符串并获取其中的有效载荷
     * @param key 有效载荷中元素的Key
     * @return 要获取的元素
     */
    public  Object getElement(String token, String key) {
        Object element;
        try{
            Claims claims = parseToken(token);
            element = claims.get(key);
        }catch(JwtException e){
            e.printStackTrace();
            return null;
        }
        return element;
    }

    public  LoginUser getElement(String token) {
        LoginUser element = new LoginUser();
        try{
            Claims claims = parseToken(token);
            SysUser sysUser = new SysUser();
            sysUser.setId(Long.valueOf(claims.get("Id").toString()));
            sysUser.setUserName( claims.get("Username").toString());
            sysUser.setPassword( claims.get("Password").toString());
            sysUser.setNickName(claims.get("Nickname").toString());
            sysUser.setPhonenumber( claims.get("Phonenumber").toString());
            Object perms = claims.get("Perms");
            element.setPermsList((List<String>)perms);
            element.setSysUser(sysUser);
        }catch(JwtException e){
            e.printStackTrace();
            return null;
        }
        return element;
    }

    /**
     * 解析Token字符串并且从其中的有效载荷中获取指定Key的元素，获取的是指定泛型类型的元素
     * @param token 解析哪个Token字符串并获取其中的有效载荷
     * @param key 有效载荷中元素的Key
     * @param clazz 指定获取元素的类型
     * @return 要获取的元素
     */
    public  T getElement(String token, String key, Class<T> clazz) {
        T element;
        try{
            Claims claims = parseToken(token);
            element = claims.get(key, clazz);
        }catch(JwtException e){
            e.printStackTrace();
            return null;
        }
        return element;
    }

    /**
     * 根据Token字符串获取其有效载荷,同时也是在校验Token的有效性
     * 需要使用特定的密钥来解析该Token字符串，该解析密钥必须与加密密钥一致
     * 如果解析失败则会抛出JwtException异常，解析失败的原因有很多种：
     * - 令牌过期
     * - 令牌签名验证不通过
     * - 令牌结构不正确
     * - 令牌有效载荷中数据的类型不匹配
     * ...
     * 抛出JwtException表示该Token无效！
     *
     * @param token 要解析的Token字符串
     * @return 要获取的有效载荷
     * @throws JwtException Token解析错误的异常信息
     */
    public  Claims parseToken(String token) throws JwtException{
        //在JwtParser解析器中配置用于解析的密钥，然后将Token字符串解析为Jws对象，最后从Jws对象中获取Claims
        Claims claims = Jwts
                .parser()//获取DefaultJwtParser
                .setSigningKey(jwtConfigTemplate.getSecret())//为获取DefaultJwtParser设置签名时使用的密钥
                .parseClaimsJws(token)//解析Token
                .getBody();//获取Claims

        return claims;
    }

    /*================================= 缓存方法 =================================*/

    /**
     * 从Token缓存中获取指定Token
     * @param object 要获取指定Token的对应的Key
     * @return Token字符串
     */
   /* public static String getTokenFromCache(Object object){
        Object rowToken = tokenCache.opsForValue().get(object);
        if(StringUtils.isEmpty(rowToken)){
            return null;
        }
        String token = rowToken.toString();
        return token;
    }*/

    /**
     * 从Token缓存中移除指定Token
     * @param key 要移除指定Token的对应的Key
     * @return 是否成功移除
     */
  /*  public static boolean removeTokenFromCache(String key){
        Boolean isDelete = tokenCache.delete(key);
        return isDelete;
    }*/

    /*================================= 属性设置器 =================================*/

    /**
     * 设置Token有效期，可以使用链式编程
     * @param expirationTime Token有效期(单位为毫秒)
     * @return 返回当前JwtUtils对象
     */
  /*  public JwtUtil setExpirationTime(long expirationTime){
        this.expirationTime = expirationTime;
        return this;
    }*/

    /**
     * 设置Jwt的加密算法，可以使用链式编程
     * @param signatureAlgorithm 加密算法
     * @return 返回当前JwtUtils对象
     */
 /*   public  JwtUtils setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm){
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }*/

    /**
     * 设置Jwt的加密密钥，可以使用链式编程
     * @param secretKey 加密密钥
     * @return 返回当前JwtUtils对象
     */
  /*  public JwtUtils setSecretKey(String secretKey){
        this.secretKey = secretKey;
        return this;
    }*/


}
