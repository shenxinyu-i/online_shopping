package com.shopcoupon.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.shopcoupon.payment", "com.shopcoupon.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shopcoupon.payment.feign")
@MapperScan("com.shopcoupon.payment.mapper")
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}
