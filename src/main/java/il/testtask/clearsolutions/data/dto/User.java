package il.testtask.clearsolutions.data.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class User {

    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Date birthDate;

    private String address;

    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phoneNumber;
}
