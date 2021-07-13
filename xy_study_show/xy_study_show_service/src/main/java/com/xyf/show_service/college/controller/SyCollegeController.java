package com.xyf.show_service.college.controller;




import com.xyf.result.R;
import com.xyf.show_service.college.service.SyCollegeService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * SyCollegeController
 *
 * @author birancloud
 * @date 2021-04-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/college" )
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

