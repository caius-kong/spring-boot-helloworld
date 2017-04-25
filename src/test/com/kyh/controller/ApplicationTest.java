package com.kyh.controller;

import com.kyh.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



/**
 * Created by kongyunhui on 2017/4/20.
 *
 * 如果不涉及service层，可以考虑使用"注释掉的@"
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@SpringBootTest(classes = MockServletContext.class)
//@WebAppConfiguration
public class ApplicationTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new SampleController()).build();
        mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSampleController() throws Exception {
        RequestBuilder request = null;
//        request = post("/sayHello")
//                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
//                .header("version", "1.0")
//                .param("name", "孔昀晖");
//        mvc.perform(request)
//                .andExpect(status().isOk())
////                .andExpect(content().string(equalTo("你好, 孔昀晖")));
//                .andDo(print());

        HttpHeaders header = new HttpHeaders();
        header.set("version", "1.0");
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);

        request = get("").headers(header);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }
}
