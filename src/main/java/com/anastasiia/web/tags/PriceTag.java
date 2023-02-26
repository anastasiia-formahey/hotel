package com.anastasiia.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;

public class PriceTag extends TagSupport {
    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(new DecimalFormat("#0").format(value) + "&#8372;");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
