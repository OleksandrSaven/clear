package clear.solution.demo.controller;

import clear.solution.demo.dto.RegistrationUserRequestDto;
import clear.solution.demo.dto.UserDto;
import clear.solution.demo.dto.UserUpdateDto;
import clear.solution.demo.dto.UserUpdateNameDto;
import clear.solution.demo.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody RegistrationUserRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable int id,
                              @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return userService.update(id, userUpdateDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateName(@PathVariable int id, @RequestBody UserUpdateNameDto updateName) {
        return userService.updateName(id, updateName);
    }

    @GetMapping
    public List<UserDto> usersByBirthDate(@RequestParam String from, String to) {
        return userService.usersByBirthDate(from, to);
    }
}
