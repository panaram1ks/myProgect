package application.spring.valid.user;

import application.spring.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, UserDTO> {
    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        return (userDTO.getPassword() != null && userDTO.getConfirmPassword()
                != null && userDTO.getPassword()
                .equals(userDTO.getConfirmPassword()));
    }
}
