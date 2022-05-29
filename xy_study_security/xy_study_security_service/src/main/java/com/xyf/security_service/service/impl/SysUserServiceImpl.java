package com.xyf.security_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.email.MailSenderUtil;
import com.xyf.redis.RedisUtil;
import com.xyf.result.CommonConstants;
import com.xyf.result.R;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.mapper.SysUserMapper;
import com.xyf.security_service.service.SysUserService;
import com.xyf.verify.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Author JiaHY
 * @Description //系统用户
 * @Date 2022/4/15 21:46
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取邮箱验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    @Override
    public R getEmailCode(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getEmail())){
            return R.failed("邮箱地址不能为空!");
        }
        String email = sysUser.getEmail();
        //检查当前邮箱是否存在
        SysUser one = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEmail,email)
                .eq(SysUser::getDelF1ag, CommonConstants.STATUS_NORMAL)
                .select(SysUser::getUserName));

        if (one != null){
            return R.failed("当前邮箱已经存在,请勿重复绑定!");
        }
        return this.getEmailCode(sysUser.getEmail(),CommonConstants.REGISTERED_CODE,"管理后台注册验证码:");
    }


    /**
     * 用户注册
     * @param sysUser
     * @return
     * JiaHY
     */
    @Override
    public R register(SysUser sysUser,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return R.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        //判断当前验证码是否存在
        boolean b = redisUtil.hasKey(CommonConstants.REGISTERED_CODE + sysUser.getEmail());
        if (b){
            String code = null;
            try {
                //获取验证码
                code = (String) redisUtil.get(CommonConstants.REGISTERED_CODE + sysUser);
            } catch (Exception e) {
                return R.failed("验证码已经过期,请重新获取!");
            }
            //获取的验证码和传过来的验证码进行比对
            if (code != null){
                if (code.equals(sysUser.getEmailCode())){
                    //查看当前用户名是否存在
                    SysUser one = this.getOne(new LambdaQueryWrapper<SysUser>()
                            .eq(SysUser::getUserName, sysUser.getUserName())
                            .eq(SysUser::getDelF1ag, CommonConstants.STATUS_NORMAL)
                            .select(SysUser::getUserName));

                    if (one != null){
                        return R.ok("当前用户名已经存在,请修改用户名后重试!");
                    }
                    //判断当前注册用户是否设置昵称
                    if (StringUtils.isBlank(sysUser.getNickName())){
                        sysUser.setNickName("神秘人");
                    }
                    //密码加密
                    String encode = bCryptPasswordEncoder.encode(sysUser.getPassword());
                    sysUser.setPassword(encode);
                    //添加用户
                    if (this.save(sysUser)){
                        Boolean emailSta = MailSenderUtil
                                .sendMailTOSingleUser(
                                        "1987228617@qq.com",
                                        "用户创建",
                                        "邮箱地址:"+sysUser.getEmail());
                        //TODO 创建成功后的处理 因为是管理后台需要绑定角色

                        return R.ok("用户注册成功!");
                    }else {
                        log.error("用户信息添加数据库失败!");
                        return R.failed("用户注册失败,请稍后重试!");
                    }
                }else {
                    return R.failed("查看验证码输入是否正确!");
                }
            }else {
                return R.failed("验证码已经过期,请重新获取!");
            }

        }else{
            return R.failed("验证码已经过期,请重新获取!");
        }

    }


    /**
     * 用户忘记密码验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    @Override
    public R getResetCode(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getEmail())){
            return R.failed("邮箱地址不能为空!");
        }
        String email = sysUser.getEmail();
        //检查当前邮箱是否存在
        SysUser one = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEmail,email)
                .eq(SysUser::getDelF1ag, CommonConstants.STATUS_NORMAL)
                .select(SysUser::getId));
        if (one == null){
            return R.failed("当前邮箱不存在!");
        }
        return this.getEmailCode(email,CommonConstants.RESET_PASSWORD_CODE,"管理后台重置密码验证码:");
    }

    /**
     * 用户重置密码
     * @param sysUser
     * @return
     * JiaHY
     */
    @Override
    public R restPassword(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getPassword())){
            return R.failed("密码不能为空!");
        }
        if (StringUtils.isBlank(sysUser.getEmail())){
            return R.failed("邮箱不能为空!");
        }
        if (StringUtils.isBlank(sysUser.getEmailCode())){
            return R.failed("验证码不能为空!");
        }
        //判断当前验证码是否存在
        boolean b = redisUtil.hasKey(CommonConstants.REGISTERED_CODE + sysUser.getEmail());
        if (b) {
            String code = null;
            try {
                //获取验证码
                code = (String) redisUtil.get(CommonConstants.REGISTERED_CODE + sysUser);
            } catch (Exception e) {
                return R.failed("验证码已经过期,请重新获取!");
            }
            //获取的验证码和传过来的验证码进行比对
            if (code != null) {
                if (code.equals(sysUser.getEmailCode())){
                   sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
                    boolean update = this.update(new LambdaUpdateWrapper<SysUser>()
                            .eq(SysUser::getEmail, sysUser.getEmail())
                            .set(SysUser::getPassword, sysUser.getPassword())
                    );
                    if (update){
                        return R.ok("密码重置成功!");
                    }else {
                        return R.failed("密码重置失败!");
                    }
                }else {
                    return R.failed("查看验证码输入是否正确!");
                }
            }else {
                return R.failed("验证码已经过期,请重新获取!");
            }
        }else {
            return R.failed("验证码已经过期,请重新获取!");
        }
    }

    /**
     * 发送邮件公共方法
     * @param email
     * @param codeType 验证码类型
     * @param title  标题
     * @return
     */
    private R getEmailCode(String email,String codeType,String title){
        boolean b = false;
        //查看当前邮箱是否存在验证码
        b = redisUtil.hasKey(codeType + email);
        if (!b){
            //发送邮件
            String code = VerifyUtil.getCode();
            Boolean emailSta = MailSenderUtil.sendMailTOSingleUser(email, title, "您的验证码为:" + code);
            if (emailSta){
                //存储验证码到redis  过期时间一分钟  过期不可用
                boolean set = redisUtil.set(code + email, code, 60);
                if (set){
                    return R.ok("验证码发送成功,请前往邮箱查看!");
                }else {
                    log.error("邮箱验证码存储失败:Redis.....");
                    return R.failed("验证码发送失败,请稍后重试!");
                }
            }else {
                return R.failed("验证码发送失败,请稍后重试!");
            }
        }else {
            return R.failed("发送太频繁,强稍后再试!");
        }

    }
}
