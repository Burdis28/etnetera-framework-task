package com.etnetera.hr.utils;

import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.FrameworkVersionDtoIn;
import com.etnetera.hr.dto.FrameworkVersionDtoOut;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoOut;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Mapper {

    public static JavaScriptFrameworkDtoOut mapFrameworkToDtoOut(JavaScriptFramework framework) {
        JavaScriptFrameworkDtoOut frameworkDtoOut = new JavaScriptFrameworkDtoOut();
        frameworkDtoOut.setName(framework.getName());
        frameworkDtoOut.setHypeLevel(framework.getHypeLevel());
        frameworkDtoOut.setDeprecationDate(framework.getDeprecationDate());
        frameworkDtoOut.setVersions(mapVersionsToDtoOut(framework.getVersion()));

        return frameworkDtoOut;
    }

    public static List<FrameworkVersionDtoOut> mapVersionsToDtoOut(List<FrameworkVersion> versionList) {
        List<FrameworkVersionDtoOut> dtoOutList = new ArrayList<>();
        for (FrameworkVersion version : versionList) {
            dtoOutList.add(mapVersionToDtoOut(version));
        }
        return dtoOutList;
    }

    private static FrameworkVersionDtoOut mapVersionToDtoOut(FrameworkVersion version) {
        FrameworkVersionDtoOut dtoOut = new FrameworkVersionDtoOut();
        dtoOut.setVersion(version.getVersion());
        dtoOut.setVersionCreateDate(version.getVersionCreateDate());
        return dtoOut;
    }

    private static FrameworkVersion mapVersionDtoInToVersion(FrameworkVersionDtoIn dtoIn) {
        FrameworkVersion frameworkVersion = new FrameworkVersion();
        frameworkVersion.setVersion(dtoIn.getVersion());
        frameworkVersion.setVersionCreateDate(ZonedDateTime.now());
        return frameworkVersion;
    }

    public static JavaScriptFramework mapFrameworkDtoInToFramework(JavaScriptFrameworkDtoIn dtoIn) {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setName(dtoIn.getName());
        framework.setHypeLevel(dtoIn.getHypeLevel());
        framework.setDeprecationDate(dtoIn.getDeprecationDate());
        for (FrameworkVersionDtoIn version : dtoIn.getVersions()) {
            framework.addVersion(mapVersionDtoInToVersion(version));
        }

        return framework;
    }
}
