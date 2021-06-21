package com.stock.dialect;

import java.util.HashSet;
import java.util.Set;

import com.stock.ticker.StockTickerProcessor;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

public class StockDialect extends AbstractProcessorDialect {

    public StockDialect() {
        super("Stock Dialect", 
                "st",
                1000); 
    }

    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new StockTickerProcessor(dialectPrefix));
        return processors;
    }
}