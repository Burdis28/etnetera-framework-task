package com.etnetera.hr.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class FrameworkVersionDtoOut {

    private BigDecimal version;
    private ZonedDateTime versionCreateDate;

    public FrameworkVersionDtoOut() {
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public ZonedDateTime getVersionCreateDate() {
        return versionCreateDate;
    }

    public void setVersionCreateDate(ZonedDateTime versionCreateDate) {
        this.versionCreateDate = versionCreateDate;
    }
}
