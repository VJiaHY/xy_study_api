package com.xyf.security_service.mapper;

import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_api.utils.JwtUtil;
import com.xyf.security_service.XyStudySercurityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XyStudySercurityApplication.class)
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Test
    public void test01(){

        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
        System.out.println(1);

    }

    @Test
    public void test02(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1234");
        boolean matches = bCryptPasswordEncoder.matches("1234", encode);
        System.out.println(matches);
        System.out.println(encode);
    }

    @Test
    public void test03(){
        List<String> permsByUserId = sysPermissionMapper.getPermsByUserId(1L);
        System.out.println(permsByUserId.toString());
    }

}