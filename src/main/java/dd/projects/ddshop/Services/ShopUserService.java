package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Mappers.ShopUserCreationDTOMapper;
import dd.projects.ddshop.Repositories.ShopUserDao;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShopUserService {

    @Autowired
    ShopUserDao shopUserDao;

    @Autowired
    ShopUserCreationDTOMapper shopUserCreationDTOMapper;

    public ResponseEntity<String> signUp(ShopUserCreationDTO shopUserCreationDTO) {
        try {
            if (!Objects.isNull(shopUserCreationDTO)) {
                ShopUser shopUser = shopUserCreationDTOMapper.toEntity(shopUserCreationDTO);
                if (Objects.isNull(shopUserDao.findByEmail(shopUser.getEmail()))) {
                    shopUserDao.save(shopUser);
                    return new ResponseEntity<String>("Successfully Registered.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(
                        "Email already in use.",
                        HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<String>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(
            "Something went wrong.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            Optional<ShopUser> optional = shopUserDao.findById(id);
            if (optional.isPresent()) {
                shopUserDao.deleteById(id);
                return new ResponseEntity<String>("User deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("User does not exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(
            "Something went wrong.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
