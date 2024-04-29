package clear.solution.demo.service;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.dto.UserUpdateDto;
import clear.solution.demo.dto.UserUpdateNameDto;
import clear.solution.demo.model.User;
import java.util.List;

public interface UserService {
    UserDto register(RegistrationUserRequestDto requestDto);

    UserDto updateName(int id, UserUpdateNameDto updateNameDto);

    UserDto update(int id, UserUpdateDto updateDto);

    List<UserDto> usersByBirthDate(String from, String to);

    void delete(int id);

    List<User> setUser(User user);
}
