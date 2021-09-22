package com.etnetera.hr.model;

import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoOut;
import com.etnetera.hr.exceptions.FrameworkValidationException;
import com.etnetera.hr.repository.JavaScriptFrameworkService;
import com.etnetera.hr.utils.Mapper;
import com.etnetera.hr.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaScriptFrameworkModel {

    private JavaScriptFrameworkService frameworkService;
    private static final String frameworkName = "frameworkTest-";

    @Autowired
    public JavaScriptFrameworkModel(JavaScriptFrameworkService frameworkService) {
        this.frameworkService = frameworkService;
    }

    /**
     * Model method to list all available frameworks.
     * @return list of DTO out framework objects.
     */
    public List<JavaScriptFramework> listFrameworks() {
        List<JavaScriptFramework> frameworkList = new ArrayList<>();
        frameworkService.listAll().iterator().forEachRemaining(frameworkList::add);

        return frameworkList;
//
//        List<JavaScriptFrameworkDtoOut> dtoOut = new ArrayList<>();
//        for (JavaScriptFramework framework : frameworkList) {
//            dtoOut.add(Mapper.mapFrameworkToDtoOut(framework));
//        }
//        return dtoOut;
    }

    /**
     * Method to initialize test data and random frameworks.
     * @param count number of frameworks to create
     * @return Confirmation of success or error message
     */
    public String initializeTestData(int count) {
        List<JavaScriptFramework> frameworkList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            frameworkList.add(createTestFramework(i));
        }
        try {
            frameworkService.create(frameworkList);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            return "Initialization of test data has failed.";
        }
        return "Successful initialization of test data.";
    }

    private JavaScriptFramework createTestFramework(int nameNum) {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setName(frameworkName + nameNum);
        framework.setDeprecationDate(ZonedDateTime.now().plusYears(2));
        framework.setHypeLevel(7);

        FrameworkVersion version = new FrameworkVersion();
        version.setVersion(BigDecimal.valueOf(1.0));
        version.setVersionCreateDate(ZonedDateTime.now());

        FrameworkVersion version2 = new FrameworkVersion();
        version2.setVersion(BigDecimal.valueOf(1.1));
        version2.setVersionCreateDate(ZonedDateTime.now());

        framework.addVersion(version);
        framework.addVersion(version2);

        return framework;
    }

    /**
     * Method for creating a new JavaScriptFramework objects.
     * @param dtoIn dtoIn
     * @return Success or error message
     */
    public String create(JavaScriptFrameworkDtoIn dtoIn) {
        // HDS 1 - Validate DtoIn for creating of a new JavaScript Framework.
        Validator.validateJavaScriptFrameworkDtoIn(dtoIn);

        // HDS 2 - Map DtoIn to entity object JavaScriptFramework.
        JavaScriptFramework framework = Mapper.mapFrameworkDtoInToFramework(dtoIn);

        // HDS 3 - Persist new object to database.
        try {
            frameworkService.create(framework);
        } catch (RuntimeException exception) {
            // AS 3.1 - Return a data persist error.
            System.out.println(exception.getMessage());
            return "Creating JavaScript Framework has failed.";
        }
        // HDS 4 - Return a success confirmation message.
        return "Successful creation of JavaScript Framework.";
    }

    /**
     * Method for editing a JavaScriptFramework object.
     * @param framework framework to be edited
     * @return Success or error message
     */
    public String edit(JavaScriptFramework framework) {
        return null;
    }

    public String delete(int id) {

        return null;
    }
}
