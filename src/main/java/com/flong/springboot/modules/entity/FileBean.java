package com.flong.springboot.modules.entity;

import lombok.Data;

@Data
public class FileBean {
    private String id;
    private String name;
    private String type;
    private String url;

    public FileBean setId(String id) {
        this.id = id;
        return this;
    }

    public FileBean setType(String type) {
        this.type = type;
        return this;
    }

    public FileBean setName(String name) {
        this.name = name;
        return this;
    }

    public FileBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


}
