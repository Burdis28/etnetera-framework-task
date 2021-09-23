package com.etnetera.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Data entity that represents Version attributes of JavaScript Framework.
 */
@Entity
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long versionId;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal version;

    @Column(nullable = false)
    private ZonedDateTime versionCreateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="javascriptframework_id", nullable=false)
    @JsonIgnore
    private JavaScriptFramework javaScriptFramework;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
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

    public JavaScriptFramework getJavaScriptFramework() {
        return javaScriptFramework;
    }

    public void setJavaScriptFramework(JavaScriptFramework javaScriptFramework) {
        this.javaScriptFramework = javaScriptFramework;
    }
}
