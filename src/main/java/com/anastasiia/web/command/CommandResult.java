package com.anastasiia.web.command;

public class CommandResult {

    String page;
    boolean isRedirect;

    public CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public String getPage() {
        page = page.replaceAll("http://localhost:9090/hotel", "");
        return page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }
}
