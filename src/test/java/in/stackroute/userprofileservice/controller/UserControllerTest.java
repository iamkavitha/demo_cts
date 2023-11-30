package in.stackroute.userprofileservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.stackroute.userprofileservice.exceptions.UserExistsException;
import in.stackroute.userprofileservice.model.User;
import in.stackroute.userprofileservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.RequestBuilder.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserProfileController.class)
class UserControllerTest {
    public static final String USERS_REGISTER = "/api/v1/users/register";
    User userONe;

    @MockBean
    private UserServiceImpl service;

    @Autowired
    private MockMvc mockMvc;// to send http requests

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
         userONe = new User(1, "testname", "test@gmail.com", "testpass");
    }


    @Test
    public void givenUserDetailsWhenUserDoesNotExistsThenShouldReturnCreatedStatus() throws Exception {

        Mockito.when(service.registeredUser(any(User.class))).thenReturn(userONe);

        mockMvc.perform(post(USERS_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userONe))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                        .andDo(MockMvcResultHandlers.print());

        verify(service).registeredUser(any(User.class));

    }

    @Test
    public void givenUserDetailsWhenUserExistsThenShouldReturnStatusConflict() throws Exception {

        Mockito.when(service.registeredUser(any(User.class))).thenThrow(UserExistsException.class);

        mockMvc.perform(post(USERS_REGISTER)
//                       "/api/v1/users/register"

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userONe))
                )
                .andExpect(status().isConflict())

                .andDo(MockMvcResultHandlers.print());

        verify(service).registeredUser(any(User.class));
    }


}

