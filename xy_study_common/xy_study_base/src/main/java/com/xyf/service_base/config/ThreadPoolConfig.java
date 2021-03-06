package com.xyf.service_base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @ClassName:  ThreadPoolConfig
 * @Description: 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {


    private final Integer poolSize = Runtime.getRuntime().availableProcessors();
    private final Integer maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private final Integer queueCapacity = 300;


    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(poolSize);
        //指定最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列中最大的数目
        executor.setQueueCapacity(queueCapacity);
        //线程名称前缀
        executor.setThreadNamePrefix("defaultThreadPool_");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(10);
        //加载
        executor.initialize();
        return executor;
    }



}
