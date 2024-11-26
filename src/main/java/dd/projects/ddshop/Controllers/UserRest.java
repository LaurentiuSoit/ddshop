package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.LoginDTO;
import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.DTOs.ShopUserDTO;
import dd.projects.ddshop.Services.ShopUserService;
import java.util.HashMap;
import java.util.Map;
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
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        try {
            return shopUserService.login(loginDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Something went wrong.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ShopUserDTO> getUser(@PathVariable Integer id) {
        try {
            return shopUserService.getUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ShopUserDTO> updateUser(
        @PathVariable Integer id,
        @RequestBody ShopUserDTO shopUserDTO
    ) {
        try {
            return shopUserService.updateUser(id, shopUserDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/updateBillingAddress")
    public ResponseEntity<ShopUserDTO> updateBillingAddress(
        @RequestParam Integer userId,
        @RequestParam Integer addressId
    ) {
        try {
            return shopUserService.updateBillingAddress(userId, addressId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/updateDeliveryAddress")
    public ResponseEntity<ShopUserDTO> updateDeliveryAddress(
        @RequestParam Integer userId,
        @RequestParam Integer addressId
    ) {
        try {
            return shopUserService.updateDeliveryAddress(userId, addressId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping(path = "/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            return shopUserService.forgotPassword(email);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(
        @RequestParam Integer id,
        @RequestParam String newPassword
    ) {
        try {
            return shopUserService.changePassword(id, newPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
