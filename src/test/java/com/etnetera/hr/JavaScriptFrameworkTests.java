package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.FrameworkFindCriteriaDtoIn;
import com.etnetera.hr.dto.FrameworkVersionDtoIn;
import com.etnetera.hr.dto.JavaScriptFrameworkDtoIn;
import com.etnetera.hr.exceptions.FrameworkValidationException;
import com.etnetera.hr.repository.JavaScriptFrameworkService;
import com.etnetera.hr.utils.TestUtils;
import com.etnetera.hr.utils.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

    @Before
    public void beforeTests() {

    }

    @After
    public void afterTests() {
        frameworkService.deleteAll();
    }

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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("Successful creation of JavaScript Framework.")));

        List<JavaScriptFramework> frameworksInDb = new ArrayList<>();
        frameworkService.listAll().spliterator().forEachRemaining(frameworksInDb::add);
        Assert.assertEquals(frameworksInDb.size(), 1);
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
            Assert.assertEquals(exception.getCause().getMessage(), FrameworkValidationException.errorPrefix + Validator.nameValidation);
        }
    }

    @Test
    public void getJavaScriptFrameworksHDSControllerTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        frameworkService.create(framework);

        mockMvc.perform(get("/frameworks"))
                .andDo(print())
                .andExpect(status().isOk());

        List<JavaScriptFramework> listFrameworks = controller.frameworks();
        Assert.assertEquals(listFrameworks.size(), 1);
        Assert.assertEquals(listFrameworks.get(0).getName(), framework.getName());
    }

    @Test
    public void deleteJavaScriptFrameworksHDSControllerTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework framework2 = getTestJavaScriptFramework();
        framework2.setName("remainingFramework");
        JavaScriptFramework createdFramework = frameworkService.create(framework);
        JavaScriptFramework createdFramework2 = frameworkService.create(framework2);

        String url = "/deleteFramework";

        mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                        .param("id", String.valueOf(createdFramework.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("Framework was successfully deleted.")));

        List<JavaScriptFramework> listFrameworks = controller.frameworks();
        Assert.assertEquals(listFrameworks.size(), 1);
        Assert.assertEquals(listFrameworks.get(0).getName(), createdFramework2.getName());
    }

    @Test
    public void deleteJavaScriptFrameworksValidationFailTest() {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework framework2 = getTestJavaScriptFramework();
        framework2.setName("remainingFramework");
        frameworkService.create(framework);
        frameworkService.create(framework2);

        String url = "/deleteFramework";

        try {
            mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                            .param("id", ""))
                    .andDo(print());
        } catch (Exception exception) {
            Assert.assertEquals(exception.getCause().getMessage(), FrameworkValidationException.errorPrefix + Validator.idFilledValidation);
        }
    }

    @Test
    public void editJavaScriptFrameworkHDSTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework createdFramework = frameworkService.create(framework);

        createdFramework.setName("changedName");

        String url = "/editFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createdFramework);

        mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("Framework was successfully edited.")));

        Assert.assertEquals(frameworkService.get(createdFramework.getId()).get().getName(), "changedName");
        Assert.assertEquals(frameworkService.get(createdFramework.getId()).get().getHypeLevel(),
                framework.getHypeLevel());
    }

    @Test
    public void editJavaScriptFrameworkValidationFailTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework createdFramework = frameworkService.create(framework);

        createdFramework.setId(null);

        String url = "/editFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createdFramework);

        try {
            mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8).content(requestJson))
                    .andDo(print());
        } catch (Exception exception) {
            Assert.assertEquals(exception.getCause().getMessage(), FrameworkValidationException.errorPrefix + Validator.idFilledValidation);
        }
    }

    @Test
    public void findJavaScriptFrameworkHDSTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework framework2 = getTestJavaScriptFramework();
        framework2.setName("otherFramework");
        JavaScriptFramework createdFramework = frameworkService.create(framework);
        JavaScriptFramework createdFramework2 = frameworkService.create(framework2);

        FrameworkFindCriteriaDtoIn dtoIn = new FrameworkFindCriteriaDtoIn();
        dtoIn.setName(framework2.getName());

        String url = "/findFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dtoIn);

        mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(createdFramework2.getId()));
    }

    @Test
    public void findJavaScriptFrameworkNothingFoundTest() throws Exception {
        JavaScriptFramework framework = getTestJavaScriptFramework();
        JavaScriptFramework framework2 = getTestJavaScriptFramework();
        framework2.setName("otherFramework");
        frameworkService.create(framework);
        frameworkService.create(framework2);

        FrameworkFindCriteriaDtoIn dtoIn = new FrameworkFindCriteriaDtoIn();
        dtoIn.setName("NonExistingFrameworksName");

        String url = "/findFramework";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dtoIn);

        mockMvc.perform(post(url).contentType(TestUtils.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").doesNotExist());
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