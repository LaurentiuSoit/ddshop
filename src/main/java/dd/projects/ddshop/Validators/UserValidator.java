package dd.projects.ddshop.Validators;

import dd.projects.ddshop.DTOs.LoginDTO;
import dd.projects.ddshop.DTOs.ShopUserCreationDTO;

public class UserValidator {

    public static boolean validateSignUp(ShopUserCreationDTO shopUserCreationDTO) {
        return (
            (shopUserCreationDTO.getFirstName() != null) &&
            (shopUserCreationDTO.getLastName() != null) &&
            (shopUserCreationDTO.getEmail() != null) &&
            (shopUserCreationDTO.getPassword() != null) &&
            (shopUserCreationDTO.getPhoneNumber() != null)
        );
    }

    public static boolean validateLogin(LoginDTO loginDTO) {
        return ((loginDTO.getEmail() != null) && (loginDTO.getPassword() != null));
    }
}
