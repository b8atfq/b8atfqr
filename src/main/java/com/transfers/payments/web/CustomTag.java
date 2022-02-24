package com.transfers.payments.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class CustomTag extends SimpleTagSupport {
    StringWriter sw = new StringWriter();
    public void doTag() throws IOException, JspException {
        getJspBody().invoke(sw);
        getJspContext().getOut().println(sw.toString());
    }
}
