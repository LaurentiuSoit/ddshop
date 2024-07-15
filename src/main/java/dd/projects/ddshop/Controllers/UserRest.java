package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserRest {
    @Autowired
    UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody ShopUserCreationDTO shopUserCreationDTO) {
        try {
            return userService.signUp(shopUserCreationDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
