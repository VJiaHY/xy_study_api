package com.xyf.verify;

import java.util.Random;

/**
 * @Author JiaHY
 * @Description 验证码工具类
 * @Date 2022/5/29 18:49
 **/
public class VerifyUtil {
    // 默认验证码字符集
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


    public  static String getCode(){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int r = random.nextInt(chars.length);
            sb.append(chars[r]);
        }
        return sb.toString();
    }

}
