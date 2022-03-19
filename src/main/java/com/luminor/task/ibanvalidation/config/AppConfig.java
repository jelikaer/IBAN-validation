package com.luminor.task.ibanvalidation.config;

import com.luminor.task.ibanvalidation.api.Item;
import com.luminor.task.ibanvalidation.api.ItemImpl1;
import com.luminor.task.ibanvalidation.api.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Item item1() {
        return new ItemImpl1();
    }

    @Bean
    public Store store() {
        return new Store(item1());
    }
}
