package org.example.rent.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.management.relation.Role;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

    private static final String PHONE_PATTERN = "^+380(?:20|39|50|6[3678]|7[357]|89|9[1-9])\\d{7}$";

    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String username;
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = PHONE_PATTERN, message = "Invalid content type")
    private String phone;
    private Role role;

}
