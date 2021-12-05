package com.hl.xinguanservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author huangliang
 * @Date 2021/11/15 10:53
 * @Version 1.0
 * @Description
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.hl"})
public class XinGuanApplication {
    public static void main(String[] args) {
        SpringApplication.run(XinGuanApplication.class,args);
    }
}
