package com.stock.dialect;

import java.util.HashSet;
import java.util.Set;

import com.stock.ticker.StockTickerProcessor;

import org.apache.velocity.app.VelocityEngine;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

public class StockDialect extends AbstractProcessorDialect {

    private VelocityEngine velocityEngine;

    public StockDialect(VelocityEngine velocityEngine) {
        super("Stock Dialect", "st", 1000);
        this.velocityEngine = velocityEngine;
    }

    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new StockTickerProcessor(dialectPrefix, velocityEngine));
        return processors;
    }
}