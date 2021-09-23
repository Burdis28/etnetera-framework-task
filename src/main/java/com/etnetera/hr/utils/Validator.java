package com.etnetera.hr.utils;

import com.etnetera.hr.dto.FrameworkVersionDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkEditDtoIn;
import com.etnetera.hr.exceptions.FrameworkValidationException;

/**
 * Validator class for validations of operations related to JavaScript Frameworks.
 */
public class Validator {

    public static final String dtoInNull = "DtoIn cannot be null.";
    public static final String nameValidation = "Name parameter in DtoIn cannot be empty.";
    public static final String hypeValidation = "HypeLevel parameter in DtoIn cannot be empty.";
    public static final String deprecationValidation = "DeprecationDate parameter in DtoIn cannot be empty.";
    public static final String versionsValidation = "Versions parameter in DtoIn cannot be empty.";
    public static final String versionsParameterValidation = "Version in DtoIn has to be a decimal number with 4 precision" +
            "and 2 scale.";
    public static final String idFilledValidation = "ID parameter has to be filled.";

    public static void validateJavaScriptFrameworkDtoIn(JavaScriptFrameworkDtoIn dtoIn) {

        // HDS 1.1 - Validate that dtoIn is not null.
        if (dtoIn == null) {
            throw new FrameworkValidationException(dtoInNull);
        }

        // HDS 1.2 - Validate that dtoIn.name is not null or empty.
        if (dtoIn.getName() == null || dtoIn.getName().isEmpty()) {
            throw new FrameworkValidationException(nameValidation);
        }

        // HDS 1.3 - Validate that dtoIn.hypeLevel is not null.
        if (dtoIn.getHypeLevel() == null) {
            throw new FrameworkValidationException(hypeValidation);
        }

        // HDS 1.4 - Validate that dtoIn.deprecationDate is not null.
        if (dtoIn.getDeprecationDate() == null) {
            throw new FrameworkValidationException(deprecationValidation);
        }

        // HDS 1.5 - Validate that dtoIn.versions is not null, empty and that version attribute
        // is valid number (precision 4 and scale 2).
        if (dtoIn.getVersions() == null || dtoIn.getVersions().isEmpty()) {
            throw new FrameworkValidationException(versionsValidation);
        }

        for (FrameworkVersionDtoIn versionDtoIn : dtoIn.getVersions()) {
            if (versionDtoIn.getVersion() == null || (versionDtoIn.getVersion().stripTrailingZeros().scale() > 2 &&
                            versionDtoIn.getVersion().stripTrailingZeros().precision() > 4)) {
                throw new FrameworkValidationException(versionsParameterValidation);
            }
        }
    }

    public static void validateEditJavaScriptFramework(JavaScriptFrameworkEditDtoIn framework) {

        // HDS 1.1 - Validate that framework is not null.
        if (framework == null) {
            throw new FrameworkValidationException(dtoInNull);
        }

        // HDS 1.2 - Validate that ID is not null.
        if (framework.getId() == null) {
            throw new FrameworkValidationException(idFilledValidation);
        }
    }

    public static void validateDeleteJavaScriptFramework(Long id) {
        // HDS 1.1 - Validate that ID is not null.
        if (id == null) {
            throw new FrameworkValidationException(idFilledValidation);
        }

    }
}
