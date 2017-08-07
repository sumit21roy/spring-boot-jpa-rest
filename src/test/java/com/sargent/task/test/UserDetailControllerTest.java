package com.sargent.task.test;

/**
 * Uses JsonPath: Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sargent.task.Application;
import com.sargent.task.api.rest.UserDetailController;
import com.sargent.task.domain.UserDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class UserDetailControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/example/v1/userDetails/[0-9]+";

    @InjectMocks
    UserDetailController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/example/v1/userDetails")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        UserDetail r1 = mockUserInfo("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        // Create fields for User
        MvcResult result = mvc.perform(post("/example/v1/userDetails")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //Retrieve fields check for user FIELDS CHECK FOR USER
        mvc.perform(get("/example/v1/userDetails/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is(r1.getName())))
                .andExpect(jsonPath("$.location", is(r1.getLocation())))
                .andExpect(jsonPath("$.text", is(r1.getText())));


        //Retrieve Status Check
        mvc.perform(get("/example/v1/userDetails/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private UserDetail mockUserInfo(String prefix) {
        UserDetail r = new UserDetail();
        r.setLocation(prefix + "_location");
        r.setText(prefix + "_text");
        r.setName(prefix + "_name");
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
