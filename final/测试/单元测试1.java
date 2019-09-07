package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import net.sf.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AdminControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Autowired
    private AdminService adminService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void adminLogin() {
        String str = "{\"username\":\"sf3\",\"password\":\"sf\"}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminLogin");
        mockHttpServletRequestBuilder.header("Origin","*").contentType(MediaType.APPLICATION_JSON_UTF8).content(str);
        try {
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(true))
                    .andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminUserList(){
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminUserList");
        mockHttpServletRequestBuilder.header("Origin", "*");
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminBookList(){
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminBookList");
        mockHttpServletRequestBuilder.header("Origin", "*");
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminBanUser(){
        String str = "{\"username\":\"sf\",\"banned\":true}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminBanOperator");
        mockHttpServletRequestBuilder.header("Origin", "*").contentType(MediaType.APPLICATION_JSON_UTF8).content(str);
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminRecoverUser(){
        String str = "{\"username\":\"sf1\",\"banned\":false}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminBanOperator");
        mockHttpServletRequestBuilder.header("Origin", "*").contentType(MediaType.APPLICATION_JSON_UTF8).content(str);
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminBanBook(){
        String str = "{\"book_id\":1}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminBanBook");
        mockHttpServletRequestBuilder.header("Origin", "*").contentType(MediaType.APPLICATION_JSON_UTF8).content(str);
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void adminRecoverBook(){
        String str = "{\"book_id\":1}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/AdminRecoverBook");
        mockHttpServletRequestBuilder.header("Origin", "*").contentType(MediaType.APPLICATION_JSON_UTF8).content(str);
        try{
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}