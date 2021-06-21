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
    private static final String ARROW_BASE = "border-left:6px solid transparent;border-right: 6px solid transparent;";
    private static final String ARROW_UP = ARROW_BASE + "border-bottom: 6px solid green;";
    private static final String ARROW_DOWN = ARROW_BASE + "border-top: 6px solid red;";
    private static final String PRICE_UP = "<p style=\"display:flex;align-items:center;color:green;\"><span style=\"display:block;width:0;height:0;"
            + ARROW_UP + "\"></span>";
    private static final String PRICE_DOWN = "<p style=\"display:flex;align-items:center;color:red;\"><span style=\"display:block;width:0;height:0;"
            + ARROW_DOWN + "\"></span>";

    private static final StringBuilder RESULT = new StringBuilder();

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

            RESULT.append((pos ? PRICE_UP : PRICE_DOWN) + price.toString() + "</p>");

            structureHandler.setBody(RESULT.toString(), false);

        } catch (IOException e) {
            structureHandler.setBody("Stock Ticket Not Found", false);
        }
        RESULT.setLength(0);
    }
}
