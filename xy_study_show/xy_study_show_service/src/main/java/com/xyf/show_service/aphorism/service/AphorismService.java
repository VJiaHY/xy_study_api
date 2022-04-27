package com.xyf.show_service.aphorism.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xyf.result.R;
import com.xyf.show_pai.domain.aphorism.CelebratedDictum;
import com.xyf.show_pai.domain.college.SyCollege;

/**
 * 名言接口
 *
 * @author birancloud
 * @date 2021-04-18
 */
public interface AphorismService extends IService<CelebratedDictum> {

    /**
     * 查看单条名言
     * @return
     * JiaHy
     */
    R aphorismOne();

}
