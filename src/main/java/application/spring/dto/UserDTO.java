package application.spring.dto;

import application.spring.valid.user.ConfirmPassword;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@NonNull))
@ConfirmPassword
public class UserDTO {
    private Integer id;
    @NonNull
    @NotBlank(message = "{not.empty}")
    @Pattern(regexp = "^[^\\s][1-9a-zA-Zа-ёА-ЯЁ_\\s]+[^\\s]+$", message = "{name.patternError}")
    @Size(min = 3, max = 50, message = "{name.size}")
    private String name;
    @NonNull
    @NotBlank(message = "{not.empty}")
    private String password;

    private String confirmPassword;

    private Collection<RoleDTO> roles;
}