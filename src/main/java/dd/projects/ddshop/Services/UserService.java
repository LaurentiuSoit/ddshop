package dd.projects.ddshop.Services;

import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Repositories.ShopUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    ShopUserDao shopUserDao;

    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignUpMap(requestMap)) {
                ShopUser shopUser = shopUserDao.findByEmail(requestMap.get("email"));
                if (Objects.isNull(shopUser)) {
                    shopUserDao.save(createShopUser(requestMap));
                    return new ResponseEntity<String>("Successfully Registered.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("Email already in use.", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("firstName") && requestMap.containsKey("lastName") && requestMap.containsKey("email")
                && requestMap.containsKey("phoneNumber") && requestMap.containsKey("password");
    }

    private ShopUser createShopUser(Map<String, String> requestMap) {
        ShopUser shopUser = new ShopUser();
        shopUser.setFirstName(requestMap.get("firstName"));
        shopUser.setLastName(requestMap.get("lastName"));
        shopUser.setEmail(requestMap.get("email"));
        shopUser.setPhoneNumber(requestMap.get("phoneNumber"));
        shopUser.setPassword(requestMap.get("password"));
        shopUser.setDefaultDeliveryAddress(null);
        shopUser.setDefaultBillingAddress(null);
        return shopUser;
    }
}
