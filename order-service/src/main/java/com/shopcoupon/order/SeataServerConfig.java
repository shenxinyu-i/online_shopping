package com.shopcoupon.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 反射方式启动嵌入式Seata Server（1.6.1版本正确入口）
 */
@Configuration
public class SeataServerConfig {

    @Bean
    public CommandLineRunner startSeataServer() {
        return args -> {
            try {
                // 加载Seata Server类
                Class<?> serverClass = Class.forName("io.seata.server.Server");
                // ✅ 正确的方法名是start，不是main
                Method startMethod = serverClass.getMethod("start", String[].class);

                // 启动参数：-p 端口号
                startMethod.invoke(null, (Object) new String[]{"-p", "8091"});

                System.out.println("✅ 嵌入式Seata Server启动成功，端口：8091");
            } catch (Exception e) {
                System.err.println("❌ 嵌入式Seata Server启动失败：" + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}