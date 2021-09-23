package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.FrameworkFindCriteriaDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoOut;
import com.etnetera.hr.dto.JavaScriptFrameworkEditDtoIn;
import com.etnetera.hr.model.JavaScriptFrameworkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
public class JavaScriptFrameworkController {

    private JavaScriptFrameworkModel frameworkModel;

    @Autowired
    public JavaScriptFrameworkController(JavaScriptFrameworkModel frameworkModel) {
        this.frameworkModel = frameworkModel;
    }

    @GetMapping("/frameworks")
    public List<JavaScriptFramework> frameworks() {
        return frameworkModel.listFrameworks();
    }

    @RequestMapping(consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST,
            value = "/createFramework")
    public String create(@RequestBody JavaScriptFrameworkDtoIn dtoIn) {
        return frameworkModel.create(dtoIn);
    }

    @RequestMapping(consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST,
            value = "/editFramework")
    public String edit(@RequestBody JavaScriptFrameworkEditDtoIn framework) {
        return frameworkModel.edit(framework);
    }

    @RequestMapping(consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST,
            value = "/deleteFramework")
    public String delete(@RequestParam(name = "id") Long id) {
        return frameworkModel.delete(id);
    }

    @RequestMapping(consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST,
            value = "/findFramework")
    public List<JavaScriptFramework> find(@RequestBody FrameworkFindCriteriaDtoIn dtoIn) {
        return frameworkModel.findByCriteria(dtoIn);
    }

    @PostMapping("/initializeTestData")
    public String initializeTestData(@RequestParam(name = "count") int frameworksCount) {
        return frameworkModel.initializeTestData(frameworksCount);
    }
}
