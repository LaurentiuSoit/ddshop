package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class ShopUserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer defaultDeliveryAddressId;
    private Integer defaultBillingAddressId;
}
