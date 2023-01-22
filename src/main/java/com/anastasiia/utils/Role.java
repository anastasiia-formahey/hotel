package com.anastasiia.utils;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN("/admin/"),
    MANAGER("/manager/"),
    CLIENT("/client/"),
    UNREGISTERED("/user/");
    private final String url;

    Role(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
