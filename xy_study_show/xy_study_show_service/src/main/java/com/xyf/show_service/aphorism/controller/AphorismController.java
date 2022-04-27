package com.xyf.show_service.aphorism.controller;

import com.xyf.result.R;
import com.xyf.show_service.aphorism.service.AphorismService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @Author JiaHY
 * @Description //TODO
 * @Date 2022/4/7 13:59
 * 名言接口
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/aphorism")

public class AphorismController {
    private final AphorismService aphorismService;

    /**
     * 查看单条名言
     * @return
     * JiaHy
     */
    @GetMapping("/aphorismOne")
    @PreAuthorize("hasAuthority('show:aphorism:one')")
    public R collegeList() {
        return aphorismService.aphorismOne();
    }

}

