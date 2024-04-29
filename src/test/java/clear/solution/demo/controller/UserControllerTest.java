package clear.solution.demo.controller;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.dto.UserUpdateDto;
import clear.solution.demo.dto.UserUpdateNameDto;
import clear.solution.demo.model.User;
import clear.solution.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @BeforeAll
    public static void beforeAll() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setEmail("test@example.com");
        user.setLastName("Doe");
        user.setFirstName("Jane");
        user.setBirthDate(LocalDate.parse("2000-04-12"));
        users.add(user);
    }



    @Test
    @DisplayName("Register new user")
    void register() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationUserRequestDto requestDto = new RegistrationUserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setFirstName("Bob");
        requestDto.setLastName("Shellcheck");
        requestDto.setBirthDate("2000-04-12");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail("test@example.com");
        expectedUserDto.setFirstName("Bob");
        expectedUserDto.setLastName("Shellcheck");
        expectedUserDto.setBirthDate("2000-04-12");

        when(userService.register(any(RegistrationUserRequestDto.class))).thenReturn(expectedUserDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        UserDto responseUserDto = objectMapper.readValue(responseBody, UserDto.class);
        assertEquals(responseUserDto, expectedUserDto);
    }

    @Test
    @DisplayName("Update use name")
    public void testUpdateUserName() throws Exception {
        int id = 0;
        ObjectMapper objectMapper = new ObjectMapper();
        UserUpdateNameDto updateNameDto = new UserUpdateNameDto();
        updateNameDto.setNewFirstName("Jane");
        updateNameDto.setNewLastName("Smith");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail("test@example.com");
        expectedUserDto.setFirstName("Bob");
        expectedUserDto.setLastName("Smith");
        expectedUserDto.setBirthDate("2000-04-12");

        expectedUserDto.setFirstName(updateNameDto.getNewFirstName());
        expectedUserDto.setLastName(updateNameDto.getNewLastName());

        when(userService.updateName(id, updateNameDto)).thenReturn(expectedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateNameDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @DisplayName("Delete user")
    void delete() throws Exception {
        int userId = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}