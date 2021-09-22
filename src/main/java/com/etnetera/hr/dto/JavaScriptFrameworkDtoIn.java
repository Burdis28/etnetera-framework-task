package com.etnetera.hr.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class JavaScriptFrameworkDtoIn {

    private String name;
    private List<FrameworkVersionDtoIn> versions;
    private Integer hypeLevel;
    private ZonedDateTime deprecationDate;

    public JavaScriptFrameworkDtoIn() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<FrameworkVersionDtoIn> getVersions() {
        return versions;
    }

    public void setVersions(List<FrameworkVersionDtoIn> versions) {
        this.versions = versions;
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
}
