package sfbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:springContext.xml"
        })
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void handlelogin() {
        String str = "{\"username\":\"sf\",\"password\":\"sf\"}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/login");
        mockHttpServletRequestBuilder.header("Origin","*").contentType(MediaType.APPLICATION_JSON).content(str);
        try {
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(MockMvcResultMatchers.jsonPath("$.resp").value("f"))
                    .andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void handleregister(){
        String str = "{\"username\":\"sf4\",\"password\":\"sf\",\"email\":\"xyzeric@sina.com\"}";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/user/register");
        mockHttpServletRequestBuilder.header("Origin","*").contentType(MediaType.APPLICATION_JSON).content(str);
        try {
            String result = mvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(MockMvcResultMatchers.jsonPath("$.resp").value("s"))
                    .andDo(print()).andReturn().getResponse().getContentAsString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}