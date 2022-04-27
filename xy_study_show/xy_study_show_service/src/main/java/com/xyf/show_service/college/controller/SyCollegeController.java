package com.xyf.show_service.college.controller;
import com.xyf.result.R;
import com.xyf.show_service.college.service.SyCollegeService;

import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;


/**
 * @Author JiaHY
 * @Description //TODO
 * @Date 2022/4/5 12:03
 * 学校
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/college")
@RefreshScope
public class SyCollegeController {
    private final SyCollegeService syCollegeService;
    /**
     * 查询college列表
     */
    @GetMapping("/collegeList")
    public R collegeList() {
        return R.ok(syCollegeService.list());
    }
}

