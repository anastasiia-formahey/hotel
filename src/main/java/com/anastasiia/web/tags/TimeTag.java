package com.anastasiia.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class TimeTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(new Date(Calendar.getInstance().getTime().getTime()));
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
