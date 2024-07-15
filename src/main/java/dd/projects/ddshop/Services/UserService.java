package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Mappers.ShopUserCreationDTOToShopUserMapper;
import dd.projects.ddshop.Repositories.ShopUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    ShopUserDao shopUserDao;

    @Autowired
    ShopUserCreationDTOToShopUserMapper shopUserCreationDTOToShopUserMapper;

    public ResponseEntity<String> signUp(ShopUserCreationDTO shopUserCreationDTO) {
        try {
            if (!Objects.isNull(shopUserCreationDTO)) {
                ShopUser shopUser = shopUserCreationDTOToShopUserMapper.toEntity(shopUserCreationDTO);
                if (Objects.isNull(shopUserDao.findByEmail(shopUser.getEmail()))) {
                    shopUserDao.save(shopUser);
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
}
