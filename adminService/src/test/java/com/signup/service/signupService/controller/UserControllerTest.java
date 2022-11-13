package com.signup.service.signupService.controller;


import com.signup.service.signupService.SignupServiceApplication;
import com.signup.service.signupService.model.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.signup.service.signupService.service.UserService.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)   // for running this with junit4
@ContextConfiguration(classes = SignupServiceApplication.class)  // context setting for the real data (added in main)
@SpringBootTest // spring test
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  // to execute the test methods in order (based on name)
public class UserControllerTest {

    // For controller based mocks (for web layer)
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext movieContext; // autowired the configuration


    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(movieContext).build();

    }


    // THESE test cases check the payload details after the controller URI is called;

    @Test
    public void verifyAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/get-users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(11)))
                .andDo(print());
    }

    // VERIFY SUCCESSFUL SIGNUP
    @Test
    public void verfiySignup() throws Exception{
        User user = new User("first_user", "pass1", "pass1", "first@gmail.com");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.username").value("first_user"))
                .andDo(print());
    }

    // VERIFY UNSUCCESSFUL SIGNUP (object id must not be defined)
    @Test
    public void verfiySignupObjectIdDefined() throws Exception{
        User user = new User(new ObjectId("636f53a49d049c667ed459e9"),"second_user", "pass1", "pass1", "first@gmail.com");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("PAYLOAD MALFORMED. OBJECT ID MUST NOT BE DEFINED !!!"))
                .andDo(print());
    }

    // VERIFY UNSUCCESSFUL SIGNUP (User with this username already present)
    @Test
    public void verfiySignupUserNamePresent() throws Exception{
        User user = new User("first_user", "pass1", "pass1", "first@gmail.com");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("User with this USERNAME is already present!!!"))
                .andDo(print());
    }

    // Successful Login
    @Test
    public void verifyLogin() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("username", "first_user");
        map.put("password","pass1");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.message").value(" Successfully Logged-in"))
                .andDo(print());
    }

    // Verify Unsuccessful login (username not defined in field)
    @Test
    public void verifyLoginUsernameNotDefined() throws Exception{
        Map<String, Object> map = new HashMap<>();
        //map.put("username", "first_user");
        map.put("password","pass1");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("PAYLOAD MALFORMED. Username Must be Provided at Login !!!"))
                .andDo(print());
    }


    // verify Unsuccessful login (User with this username NOT FOUND)
    @Test
    public void verifyLoginUsernameNotFound() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("username", "random_user");
        map.put("password","pass1");


        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("User with this username NOT FOUND"))
                .andDo(print());
    }

}
