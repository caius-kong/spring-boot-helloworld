package com.kyh.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



/**
 * Created by kongyunhui on 2017/4/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class ApplicationTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new SampleController()).build();
    }

    @Test
    public void testSampleController() throws Exception {
        RequestBuilder request = null;
        request = post("/sayHello")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("version", "1.0")
                .param("name", "孔昀晖");
        mvc.perform(request)
                .andExpect(status().isOk())
//                .andExpect(content().string(equalTo("你好, 孔昀晖")));
                .andDo(print());
    }
}
