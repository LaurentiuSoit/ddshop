package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class AddressDTO {
    private String streetLine;
    private Integer postalCode;
    private String city;
    private String county;
    private String country;
}
