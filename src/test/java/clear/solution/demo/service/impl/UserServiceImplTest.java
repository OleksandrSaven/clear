package clear.solution.demo.service.impl;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.dto.UserUpdateNameDto;
import clear.solution.demo.mapper.UserMapper;
import clear.solution.demo.model.User;
import clear.solution.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserMapper userMapper = mock(UserMapper.class);
    @InjectMocks
    private UserService userService = new UserServiceImpl(userMapper);
    private List<User> users;

    @BeforeEach
    void setUp() {
        setup();
    }

    @Test
    void register_success() {
        RegistrationUserRequestDto requestDto = new RegistrationUserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setBirthDate("2000-01-01");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail("test@example.com");
        expectedUserDto.setFirstName("John");
        expectedUserDto.setLastName("Doe");
        expectedUserDto.setBirthDate("2000-01-01");

        User expectedUser = new User();
        expectedUser.setEmail("test@example.com");
        expectedUser.setFirstName("John");
        expectedUser.setLastName("Doe");
        expectedUser.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userMapper.toModel(requestDto)).thenReturn(expectedUser);
        when(userMapper.toDto(expectedUser)).thenReturn(expectedUserDto);

        UserDto result = userService.register(requestDto);

        assertEquals(expectedUserDto, result);
    }

    @Test
    void register_exist_user() {
        RegistrationUserRequestDto requestDto = new RegistrationUserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setBirthDate("2020-01-01");

        User existUser = new User();
        existUser.setEmail("test@example.com");
        existUser.setFirstName("John");
        existUser.setLastName("Doe");
        existUser.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userMapper.toModel(requestDto)).thenReturn(existUser);

        userService.register(requestDto);

        RuntimeException expectedException = assertThrows(
                RuntimeException.class,
                () -> userService.register(requestDto));
        assertEquals("User already exist with email: " + requestDto.getEmail(), expectedException.getMessage());
    }

    @Test
    void testUpdateName() {
        int userId = 0;
        UserUpdateNameDto updateNameDto = new UserUpdateNameDto();
        updateNameDto.setNewFirstName("Jane");
        updateNameDto.setNewLastName("Doe");

        User updatedUser = new User();
        updatedUser.setFirstName(updateNameDto.getNewFirstName());
        updatedUser.setLastName(updateNameDto.getNewLastName());

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setFirstName(updateNameDto.getNewFirstName());
        expectedUserDto.setLastName(updateNameDto.getNewLastName());

        when(userService.updateName(userId, updateNameDto)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.updateName(userId, updateNameDto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    private void setup() {
        users = new ArrayList<>();
        User user = new User();
        user.setEmail("test1@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.parse("2001-02-02"));
        users.add(user);
        userService.setUser(user);
    }
}
