package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Services.ShopUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserRest {

    ShopUserService shopUserService;

    public UserRest(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody ShopUserCreationDTO shopUserCreationDTO) {
        try {
            return shopUserService.signUp(shopUserCreationDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(
            "Something went wrong.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            return shopUserService.deleteUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(
            "Something went wrong.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
