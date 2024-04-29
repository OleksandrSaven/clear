package clear.solution.demo.mapper;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setBirthDate(String.valueOf(user.getBirthDate()));
        userDto.setAddress(user.getAddress());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    @Override
    public User toModel(RegistrationUserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        LocalDate birthDate = LocalDate.parse(userRequestDto.getBirthDate(),
                DateTimeFormatter.ofPattern(DATE_PATTERN));
        user.setBirthDate(birthDate);
        return user;
    }
}
