package com.netcracker;

import com.netcracker.config.AppConfig;
import com.netcracker.service.monitoringservice.impl.WatcherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@Configuration
public class NetcrackerTestTaskApplication implements CommandLineRunner{
    @Autowired
    private WatcherServiceImpl watcherService;
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{NetcrackerTestTaskApplication.class, AppConfig.class}, args);
    }

    @Override
    public void run(String... strings) {
        new Thread(watcherService).start();
    }
}