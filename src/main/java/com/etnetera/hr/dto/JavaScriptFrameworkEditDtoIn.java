package com.etnetera.hr.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class JavaScriptFrameworkEditDtoIn {

    private Long id;
    private String name;
    private Integer hypeLevel;
    private ZonedDateTime deprecationDate;

    public JavaScriptFrameworkEditDtoIn() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(Integer hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    public ZonedDateTime getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(ZonedDateTime deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
