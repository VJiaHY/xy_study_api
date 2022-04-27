package com.xyf.show_service.server;

import com.xyf.monitor.MonitorServerUtils;
import com.xyf.result.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author JiaHY
 * @Description //TODO
 * @Date 2022/4/5 18:15
 * 服务器监控请求接口
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class ServerController {

    /**
     * 查看服务器状态
     * @return
     * JiaHY
     */
    @GetMapping
    public R getServerStatus(){
        MonitorServerUtils monitorServerUtils = new MonitorServerUtils();
        return R.ok(monitorServerUtils.getServerStatus());
    }


}
