package com.xyf.gateway_service.utils;

import com.xyf.redis.RedisUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author JiaHY
 * @Description //TODO
 * @Date 2022/4/3 21:21
 * 过滤器工具类
 **/

public class IpBalckUtile {
    private static final Integer TIMESECOND = 30;
    private static final Integer MAX = 50;
    @Lazy
    @Resource
    private final   RedisUtil redisUtil;

    public IpBalckUtile(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * @param ip 访问机器的ip
     * @return JiaHY
     */
    public Boolean checkIp(String ip) {
        if (ip.contains("/")){
            ip = ip.replace("/","");
        }
        boolean b = false;
        //先判断当前ip是否存在于黑名单
        boolean balckIpEx = redisUtil.hasKey("balck_ip");
        if (balckIpEx) {
            List<Object> balckIp = redisUtil.lGet("balck_ip", 0, -1);
            if (!balckIp.isEmpty()) {
                if (balckIp.contains(ip)) {
                    return b;
                }
            }
        }
        //判断当前ip 30秒内访问次数
        //超过限制次数加入黑名单
        boolean ipEx = redisUtil.hasKey(ip);
        Integer sco = 0;
        if (ipEx) {
            long expire = 0;
            try {
                expire = redisUtil.getExpire(ip);
            } catch (Exception e) {
                b = redisUtil.set(ip, 1, TIMESECOND);
            }
            Object o = redisUtil.get(ip);
            sco = (Integer) o;
            if (sco < MAX && expire != 0) {
               b = redisUtil.set(ip,sco+=1,expire);
            }
            if (sco == MAX  && expire != 0) {
                b = redisUtil.lSet("balck_ip", ip);
            }
        } else {
            b = redisUtil.set(ip, 1, TIMESECOND);
        }
        return b;
    }


}
