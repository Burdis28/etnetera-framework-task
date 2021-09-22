package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.FrameworkVersionDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.repository.JavaScriptFrameworkService;
import com.etnetera.hr.utils.TestUtils;
import com.etnetera.hr.utils.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {

    private static final String frameworkTestName = "TestFramework";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JavaScriptFrameworkService frameworkService;

    @Autowired
    private JavaScriptFrameworkController controller;

    @Test
    public void createJavaScriptFrameworkHDSControllerTest() throws Exception {
        JavaScriptFrameworkDtoIn dtoIn = createTestFrameworkDtoIn();

        String url = "/createFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dtoIn);

        mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("Successful creation of JavaScript Framework.")));
    }

    @Test
    public void createJavaScriptFrameworkValidationFailTest() throws Exception {
        JavaScriptFrameworkDtoIn dtoIn = createTestFrameworkDtoIn();
        dtoIn.setName(null);

        String url = "/createFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dtoIn);

        try {
            mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                    .content(requestJson));
        } catch (Exception exception) {
            Assert.assertEquals(exception.getCause().getMessage(), Validator.nameValidation);
        }
    }

    @Test
    public void getJavaScriptFrameworksHDSControllerTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        frameworkService.create(framework);

        this.mockMvc.perform(get("/frameworks"))
                .andDo(print())
                .andExpect(status().isOk());

        List<JavaScriptFramework> listFrameworks = controller.frameworks();
        Assert.assertEquals(listFrameworks.size(), 1);
        Assert.assertEquals(listFrameworks.get(0).getName(), framework.getName());
    }

    private JavaScriptFrameworkDtoIn createTestFrameworkDtoIn() {
        JavaScriptFrameworkDtoIn dtoIn = new JavaScriptFrameworkDtoIn();
        dtoIn.setName(frameworkTestName);
        dtoIn.setHypeLevel(8);
        dtoIn.setDeprecationDate(ZonedDateTime.now().plusYears(2));
        dtoIn.setVersions(createFrameworkVersionsDtoIn());
        return dtoIn;
    }

    private List<FrameworkVersionDtoIn> createFrameworkVersionsDtoIn() {
        List<FrameworkVersionDtoIn> versionsDtoIn = new ArrayList<>();
        createVersionDtoIn(versionsDtoIn, 1.8);
        createVersionDtoIn(versionsDtoIn, 2.1);
        return versionsDtoIn;
    }

    private void createVersionDtoIn(List<FrameworkVersionDtoIn> versionsDtoIn, double v) {
        FrameworkVersionDtoIn versionDtoIn = new FrameworkVersionDtoIn();
        versionDtoIn.setVersion(BigDecimal.valueOf(v));
        versionsDtoIn.add(versionDtoIn);
    }

    private JavaScriptFramework getTestJavaScriptFramework() {
        JavaScriptFramework framework = new JavaScriptFramework();
        framework.setDeprecationDate(ZonedDateTime.now().plusYears(1));
        framework.setName(frameworkTestName);
        framework.setHypeLevel(8);
        FrameworkVersion version = new FrameworkVersion();
        version.setVersion(BigDecimal.valueOf(3.1));
        version.setVersionCreateDate(ZonedDateTime.now());
        framework.addVersion(version);
        return framework;
    }
}
