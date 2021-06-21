package com.stock.ticker;

import java.io.IOException;
import java.math.BigDecimal;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockTickerProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "ticker";
    private static final int PRECEDENCE = 10000;

    public StockTickerProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {
        try {
            Stock stock = YahooFinance.get(HtmlEscape.escapeHtml5(attributeValue));
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            boolean pos = change.compareTo(BigDecimal.ZERO) > 0;
            String styles, color;
            if (pos) {
                color = "green";
                styles = "border-left:8px solid transparent;border-right: 8px solid transparent;border-bottom: 8px solid green;";
            } else {
                color = "red";
                styles = "border-left:8px solid transparent;border-right: 8px solid transparent;border-top: 8px solid red;";
            }

            structureHandler.setBody("<p style=\"display:flex;align-items:center;color:" + color
                    + ";\"><span style=\"display:block;width:0;height:0;" + styles + "\"></span>" + price.toString()
                    + "</p>", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
