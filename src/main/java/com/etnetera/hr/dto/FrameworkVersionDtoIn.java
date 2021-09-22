package com.etnetera.hr.dto;

import java.math.BigDecimal;

public class FrameworkVersionDtoIn {

    private BigDecimal version;

    public FrameworkVersionDtoIn() {
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}
