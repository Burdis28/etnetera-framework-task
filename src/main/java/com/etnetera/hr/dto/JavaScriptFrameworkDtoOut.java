package com.etnetera.hr.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class JavaScriptFrameworkDtoOut {

    private String name;
    private List<FrameworkVersionDtoOut> versions;
    private Integer hypeLevel;
    private ZonedDateTime deprecationDate;

    public JavaScriptFrameworkDtoOut() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FrameworkVersionDtoOut> getVersions() {
        return versions;
    }

    public void setVersions(List<FrameworkVersionDtoOut> versions) {
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
