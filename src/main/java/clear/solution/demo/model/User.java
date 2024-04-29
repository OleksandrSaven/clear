package clear.solution.demo.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
