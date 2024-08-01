package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class LoginDTO {

    private Integer id;
    private String email;
    private String password;
}
