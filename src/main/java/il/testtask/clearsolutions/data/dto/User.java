package il.testtask.clearsolutions.data.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "firstName")
    private String firstName;

    @NotBlank
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    @Column(name = "phoneNumber")
    private String phoneNumber;
}
