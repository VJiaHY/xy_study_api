package com.xyf.show_service.aphorism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.redis.RedisUtil;
import com.xyf.result.R;
import com.xyf.show_pai.config.template.DatasourceTemplate;
import com.xyf.show_pai.domain.aphorism.CelebratedDictum;
import com.xyf.show_service.aphorism.mapper.AphorismMapper;
import com.xyf.show_service.aphorism.service.AphorismService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


/**
 *SyCollegeServiceImpl业务层处理
 *
 * @author birancloud
 * @date 2022-
 */
@Service
@AllArgsConstructor
public class AphorismServiceImpl extends ServiceImpl<AphorismMapper, CelebratedDictum> implements AphorismService {

    private final static Logger logger = LoggerFactory.getLogger(AphorismServiceImpl.class);
    private final AphorismMapper aphorismMapper;

    @Lazy
    private final RedisUtil redisUtil;
    /**
     * 查看单条名言
     * @return
     * JiaHy
     */
    @Override
    @Transactional
    public R aphorismOne() {
        CelebratedDictum aphorismOne = aphorismMapper.getAphorismOne();
        if (aphorismOne == null){
            aphorismOne = new CelebratedDictum();
            aphorismOne.setCdText("终有弱水替沧海,再无相思寄巫山。");
            aphorismOne.setCdFrom( "离思五首·其四");
            if(this.save(aphorismOne)){
                return R.ok(aphorismOne);
            } else {
                logger.error("数据插入失败----> AphorismServiceImpl.aphorismOne() 1");
                return R.failed("数据查询失败!");
            }

        }else {
            return R.ok(aphorismOne);
        }
    }
    private volatile static boolean flag = true;


}






