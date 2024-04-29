package clear.solution.demo.service.impl;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.dto.UserUpdateDto;
import clear.solution.demo.dto.UserUpdateNameDto;
import clear.solution.demo.exception.EntityNotFoundException;
import clear.solution.demo.mapper.UserMapper;
import clear.solution.demo.model.User;
import clear.solution.demo.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final UserMapper userMapper;
    private List<User> users = new ArrayList<>();

    @Value("${year}")
    private int years;

    public List<User> setUser(User user) {
        users.add(user);
        return users;
    }

    @Override
    public UserDto register(RegistrationUserRequestDto requestDto) {
        if (users.stream().noneMatch(user -> user.getEmail().equals(requestDto.getEmail()))) {
            LocalDate currentDate = LocalDate.now();
            LocalDate eighteenYearsAgo = currentDate.minusYears(years);
            LocalDate birthDate = LocalDate.parse(requestDto.getBirthDate(),
                    DateTimeFormatter.ofPattern(DATE_PATTERN));
            if (birthDate.isBefore(eighteenYearsAgo)) {
                users.add(userMapper.toModel(requestDto));
            } else {
                throw new RuntimeException("You must be older then " + years);
            }
            return userMapper.toDto(users.stream()
                    .filter(user -> user.getEmail().equals(requestDto.getEmail()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("User not found with email: "
                            + requestDto.getEmail())));
        } else {
            throw new RuntimeException("User already exist with email: " + requestDto.getEmail());
        }
    }

    @Override
    public UserDto updateName(int id, UserUpdateNameDto updateNameDto) {
        User user = users.get(id);
        user.setFirstName(updateNameDto.getNewFirstName());
        user.setLastName(updateNameDto.getNewLastName());
        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(int id, UserUpdateDto updateDto) {
        User user = users.get(id);
        user.setEmail(updateDto.getEmail());
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setBirthDate(LocalDate.parse(updateDto.getBirthDate()));
        user.setAddress(updateDto.getAddress());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> usersByBirthDate(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        if (fromDate.isBefore(toDate)) {
            return users.stream().filter(d -> d.getBirthDate().isAfter(fromDate)
                            && d.getBirthDate().isBefore(toDate))
                    .map(userMapper::toDto)
                    .toList();
        } else {
            throw new RuntimeException("Incorrect date");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= users.size()) {
            User user = users.get(id);
            users.remove(user);
        } else {
            throw new RuntimeException("Index out of bound");
        }
    }
}
