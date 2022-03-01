package com.org.jp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.xy.api.utils.GlobalExceptionController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.org.jp"}, basePackageClasses = {GlobalExceptionController.class})
public class RestaurantServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServerApplication.class, args);
    }

}
