package dd.projects.ddshop.Validators;

import dd.projects.ddshop.DTOs.LoginDTO;
import dd.projects.ddshop.DTOs.ShopUserCreationDTO;

public class UserValidator {

    public static boolean validateSignUp(ShopUserCreationDTO shopUserCreationDTO) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return (
            (shopUserCreationDTO.getFirstName() != null) &&
            (shopUserCreationDTO.getLastName() != null) &&
            (shopUserCreationDTO.getEmail() != null) &&
            (shopUserCreationDTO.getPassword() != null) &&
            (shopUserCreationDTO.getPhoneNumber() != null) &&
            shopUserCreationDTO.getEmail().matches(emailRegex)
        );
    }

    public static boolean validateLogin(LoginDTO loginDTO) {
        return ((loginDTO.getEmail() != null) && (loginDTO.getPassword() != null));
    }
}
