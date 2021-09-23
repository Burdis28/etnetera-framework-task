package com.etnetera.hr.model;

import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.FrameworkFindCriteriaDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoOut;
import com.etnetera.hr.dto.JavaScriptFrameworkEditDtoIn;
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
import java.util.Optional;

@Component
public class JavaScriptFrameworkModel {

    private JavaScriptFrameworkService frameworkService;
    private static final String frameworkTestName = "frameworkTest-";

    @Autowired
    public JavaScriptFrameworkModel(JavaScriptFrameworkService frameworkService) {
        this.frameworkService = frameworkService;
    }

    /**
     * Model method to list all available frameworks.
     * @return list of DTO out framework objects.
     */
    public List<JavaScriptFramework> listFrameworks() {
        // HDS 1 - Prepare a list of JavaScriptFramework objects
        List<JavaScriptFramework> frameworkList = new ArrayList<>();
        // HDS 2 - Fill the list with values from database using service layer.
        frameworkService.listAll().iterator().forEachRemaining(frameworkList::add);

        // HDS 3 - Return a list of existing JavaScriptFramework objects.
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
    //TODO - delete in production version
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

    //TODO - delete in production version
    private JavaScriptFramework createTestFramework(int nameNum) {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setName(frameworkTestName + nameNum);
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
            throw new RuntimeException("Creating JavaScript Framework has failed.");
        }
        // HDS 4 - Return a success confirmation message.
        return "Successful creation of JavaScript Framework.";
    }

    /**
     * Method for editing a JavaScriptFramework object.
     * @param newFramework framework to be edited
     * @return Success or error message
     */
    public String edit(JavaScriptFrameworkEditDtoIn newFramework) {
        // HDS 1 - Validate input framework object.
        Validator.validateEditJavaScriptFramework(newFramework);

        // HDS 2 - Obtain a JavaScriptFramework object from database that corresponds to given framework (use ID indicator).
        Optional<JavaScriptFramework> framework = frameworkService.get(newFramework.getId());

        // HDS 3 - Set all attributes that are filled in dtoIn to Framework obtained from database.
        if (framework.isEmpty()) {
            // AS 3.1 - If no framework is out, throw an exception with appropriate message.
            throw new RuntimeException("JavaScript Framework with given id: " + newFramework.getId() +
                    " was not found and could not be edited.");
        } else {
            if (newFramework.getName() != null && !newFramework.getName().isEmpty()) {
                framework.get().setName(newFramework.getName());
            }
            if (newFramework.getDeprecationDate() != null) {
                framework.get().setDeprecationDate(newFramework.getDeprecationDate());
            }
            if (newFramework.getHypeLevel() != null) {
                framework.get().setHypeLevel(newFramework.getHypeLevel());
            }
        }
        // HDS 4 - Save a JavaScriptFramework with changed attributes.
        frameworkService.update(framework.get());

        // HDS 5 - Return a success message.
        return "Framework was successfully edited.";
    }

    /**
     * Method for deleting specific JavaScript Framework based on given ID.
     * @param id primary identificator
     * @return success message
     */
    public String delete(Long id) {
        // HDS 1 - Validate input ID.
        Validator.validateDeleteJavaScriptFramework(id);

        // HDS 2 - Delete framework.
        frameworkService.delete(id);

        // HDS 3 - Return a success message.
        return "Framework was successfully deleted.";
    }

    /**
     * Method for finding JavaScript Frameworks based on given criteria.
     * @param dtoIn dtoIn object
     * @return list of found objects
     */
    public List<JavaScriptFramework> findByCriteria(FrameworkFindCriteriaDtoIn dtoIn) {
        // HDS 1 - Filter all frameworks based on given criteria from DtoIn.
        List<JavaScriptFramework> frameworks = frameworkService.listByCriteria(dtoIn);

        // HDS 2 - Return frameworks.
        return frameworks;
    }
}
