package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class ShopUserCreationDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
