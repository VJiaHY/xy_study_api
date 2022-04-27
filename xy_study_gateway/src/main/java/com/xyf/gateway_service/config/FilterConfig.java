package com.xyf.gateway_service.config;

import com.alibaba.fastjson.JSON;
import com.xyf.gateway_service.utils.IpBalckUtile;
import com.xyf.redis.RedisUtil;
import com.xyf.result.R;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Configuration
public class FilterConfig {
    @Bean
    public IpBalckUtile ipBalckUtile(){
        return new IpBalckUtile(redisUtil());
    }
    @Bean
    public RedisUtil redisUtil(){
        return new RedisUtil();
    }
    /**
     * IP过滤器
     * 30秒内访问50次
     * @return
     */
    @Bean
    @Order(1)
    public GlobalFilter getIpFilter(){
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                InetSocketAddress remoteAddress = request.getRemoteAddress();
                Boolean isBack = ipBalckUtile().checkIp(String.valueOf(remoteAddress.getAddress()));
                ServerHttpResponse response = exchange.getResponse();
                if(isBack) {
                    return chain.filter(exchange);
                }else {
                    response.getHeaders().add("Content-Type", "application/json");
                    return response.writeWith(Mono.just(
                            response.bufferFactory().wrap(
                                    JSON.toJSONString(R.failed("你已经被禁止访问,请联系管理员")).getBytes())));
                }
            }
        };
    }
    @Bean
    @Order(2)
    public  GlobalFilter getUrlFilter(){
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("经过第2个过滤器UrlFilter");
                ServerHttpRequest request = exchange.getRequest();
                String url = request.getURI().getPath();
                System.out.println("url:"+url);
                return chain.filter(exchange);
            }
        };

    }


}
