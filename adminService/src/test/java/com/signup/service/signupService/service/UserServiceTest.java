package com.signup.service.signupService.service;

import com.google.gson.Gson;
import com.signup.service.signupService.model.User;
import com.signup.service.signupService.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    // dummy data
    @Mock
    private UserRepository repository;

    // dummy service for injecting data
    @InjectMocks
    private UserService service;


    // before each test case, ready the mocked data
    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(new User(new ObjectId("636dcf444b7e8832baeb2607"),"first_user", "pass1", "pass1"));
        userList.add(new User(new ObjectId("636dcf444b7e8832baeb2608"),"second_user", "pass2", "pass2", "first@gmail.com"));
        userList.add(new User(new ObjectId("636dcf444b7e8832baeb2609"),"third_user", "pass3", "pass3"));

        when(repository.findAll()).thenReturn(userList);

        List<User> employeesResult = service.getUsers();

        assertEquals(3, employeesResult.size());
    }

    // test for successful signup
    @Test
    public void signupTest(){
        User user = new User(new ObjectId("636dcf444b7e8832baeb2609"),"second_user", "pass2", "pass2", "first@gmail.com");

        when(repository.save(user)).thenReturn(user);

        String res = service.signupService(user);

        assertEquals(true, res.contains("Successfully"));
    }

    // test for unsuccessful signup (password not matching)
    @Test
    public void signupPasswordMatchTest(){
        User user = new User(new ObjectId("636dcf444b7e8832baeb2610"),"second_user", "pass2", "pass3", "first@gmail.com");

        when(repository.save(user)).thenReturn(user);

        String res = service.signupService(user);

        assertEquals(true, res.contains("Error!!"));
    }

    // test for successful login
    @Test
    public void loginTest(){

        User user = new User(new ObjectId("636f53a49d049c667ed459e2"),"bob102", "asdfgh", "asdfgh", "first@gmail.com");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(repository.loginUser("bob102")).thenReturn(userList);

        String res = service.loginService("bob102", "asdfgh");

        assertEquals(true, res.contains("Successfully"));
    }


    // for unsuccessful login (password incorrect)
    @Test
    public void loginFailedTest(){

        User user = new User(new ObjectId("636f53a49d049c667ed459e2"),"bob102", "asdfgh", "asdfgh", "first@gmail.com");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(repository.loginUser("bob102")).thenReturn(userList);

        String res = service.loginService("bob102", "random_password");

        assertEquals(true, res.contains("Incorrect"));
    }

}
