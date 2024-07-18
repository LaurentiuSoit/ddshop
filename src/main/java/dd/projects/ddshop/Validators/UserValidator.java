package dd.projects.ddshop.Validators;

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
}
