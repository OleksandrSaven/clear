package clear.solution.demo.mapper;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.model.User;

public interface UserMapper {

    UserDto toDto(User user);

    User toModel(RegistrationUserRequestDto userRequestDto);
}
