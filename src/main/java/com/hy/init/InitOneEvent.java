package com.hy.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by yyy1867 on 2016/12/21.
 */
@Component
@Order(value = 1)
public class InitOneEvent implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("CommandLineRunner...");
    }
}
