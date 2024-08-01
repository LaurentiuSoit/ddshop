package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.LoginDTO;
import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Mappers.ShopUserCreationDTOMapper;
import dd.projects.ddshop.Repositories.ShopUserDao;
import dd.projects.ddshop.Utils.DDShopUtils;
import dd.projects.ddshop.Validators.UserValidator;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShopUserService {

    ShopUserDao shopUserDao;

    ShopUserCreationDTOMapper shopUserCreationDTOMapper;

    public ShopUserService(
        ShopUserDao shopUserDao,
        ShopUserCreationDTOMapper shopUserCreationDTOMapper
    ) {
        this.shopUserDao = shopUserDao;
        this.shopUserCreationDTOMapper = shopUserCreationDTOMapper;
    }

    public ResponseEntity<String> signUp(ShopUserCreationDTO shopUserCreationDTO) {
        try {
            if (!Objects.isNull(shopUserCreationDTO)) {
                if (UserValidator.validateSignUp(shopUserCreationDTO)) {
                    ShopUser shopUser = shopUserCreationDTOMapper.toEntity(shopUserCreationDTO);
                    if (Objects.isNull(shopUserDao.findByEmail(shopUser.getEmail()))) {
                        shopUser.setPassword(DDShopUtils.encodePasswordMD5(shopUser.getPassword()));
                        shopUserDao.save(shopUser);
                        return new ResponseEntity<>("Successfully Registered.", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(
                            "Email already in use.",
                            HttpStatus.BAD_REQUEST
                        );
                    }
                } else {
                    return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> login(LoginDTO loginDTO) {
        try {
            if (!Objects.isNull(loginDTO)) {
                if (UserValidator.validateLogin(loginDTO)) {
                    ShopUser shopUser = shopUserDao.findByEmail(loginDTO.getEmail());
                    if (!Objects.isNull(shopUser)) {
                        String response = DDShopUtils.encodePasswordMD5(
                                loginDTO.getPassword()
                            ).equals(shopUser.getPassword())
                            ? "Successfully Logged in."
                            : "Wrong credentials.";
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else return new ResponseEntity<>(
                        "Email does not exist.",
                        HttpStatus.BAD_REQUEST
                    );
                } else {
                    return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            Optional<ShopUser> optional = shopUserDao.findById(id);
            if (optional.isPresent()) {
                shopUserDao.deleteById(id);
                return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User does not exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
