package com.stock.ticker;

import java.io.StringWriter;
import java.math.BigDecimal;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import org.unbescape.html.HtmlEscape;

public class StockTickerProcessor extends AbstractAttributeTagProcessor {

    private final VelocityEngine velocityEngine;
    private static final String ATTR_NAME = "ticker";
    private static final int PRECEDENCE = 10000;

    public StockTickerProcessor(final String dialectPrefix, VelocityEngine velocityEngine) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
        this.velocityEngine = velocityEngine;
    }

    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {
        try {
            Stock stock = YahooFinance.get(HtmlEscape.escapeHtml5(attributeValue));
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            boolean trend = change.compareTo(BigDecimal.ZERO) > 0;

            Template t = velocityEngine
                    .getTemplate("velocity/" + (trend ? "ticker_uptrend.vm" : "ticker_downtrend.vm"));

            VelocityContext vcontext = new VelocityContext();
            vcontext.put("ticker", price);

            StringWriter writer = new StringWriter();
            t.merge(vcontext, writer);
            structureHandler.setBody(writer.toString(), false);
        } catch (Exception e) {
            structureHandler.setBody("Ticket Exception", false);
        }
    }
}
