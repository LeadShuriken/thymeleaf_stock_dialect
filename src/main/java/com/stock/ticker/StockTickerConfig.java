package com.stock.ticker;

import com.stock.dialect.StockDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockTickerConfig {

    @Bean
    public StockDialect stockDialect() {
        return new StockDialect();
    }
}
