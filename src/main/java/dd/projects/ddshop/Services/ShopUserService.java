package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.LoginDTO;
import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.DTOs.ShopUserDTO;
import dd.projects.ddshop.Entities.Address;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Mappers.ShopUserCreationDTOMapper;
import dd.projects.ddshop.Mappers.ShopUserDTOMapper;
import dd.projects.ddshop.Repositories.AddressDao;
import dd.projects.ddshop.Repositories.CartDao;
import dd.projects.ddshop.Repositories.ShopUserDao;
import dd.projects.ddshop.Utils.DDShopUtils;
import dd.projects.ddshop.Validators.UserValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ShopUserService {

    JavaMailSender javaMailSender;
    ShopUserDao shopUserDao;
    CartDao cartDao;

    AddressDao addressDao;

    ShopUserCreationDTOMapper shopUserCreationDTOMapper;

    ShopUserDTOMapper shopUserDTOMapper;

    public ShopUserService(
        JavaMailSender javaMailSender,
        ShopUserDao shopUserDao,
        CartDao cartDao,
        AddressDao addressDao,
        ShopUserCreationDTOMapper shopUserCreationDTOMapper,
        ShopUserDTOMapper shopUserDTOMapper
    ) {
        this.javaMailSender = javaMailSender;
        this.shopUserDao = shopUserDao;
        this.cartDao = cartDao;
        this.addressDao = addressDao;
        this.shopUserCreationDTOMapper = shopUserCreationDTOMapper;
        this.shopUserDTOMapper = shopUserDTOMapper;
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

    public ResponseEntity<Map<String, String>> login(LoginDTO loginDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!Objects.isNull(loginDTO)) {
                if (UserValidator.validateLogin(loginDTO)) {
                    ShopUser shopUser = shopUserDao.findByEmail(loginDTO.getEmail());
                    if (!Objects.isNull(shopUser)) {
                        response.put(
                            "message",
                            DDShopUtils.encodePasswordMD5(loginDTO.getPassword()).equals(
                                    shopUser.getPassword()
                                )
                                ? "Logged in."
                                : "Wrong credentials."
                        );
                        response.put("id", shopUser.getId().toString());
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        response.put("message", "Account does not exist.");
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    response.put("message", "Bad Request.");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.put("message", "Bad Request.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.put("message", "Something went wrong.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ShopUserDTO> getUser(Integer id) {
        try {
            Optional<ShopUser> optional = shopUserDao.findById(id);
            if (optional.isPresent()) {
                ShopUserDTO shopUserDTO = shopUserDTOMapper.toDTO(optional.get());
                if (
                    !Objects.isNull(optional.get().getDefaultBillingAddress())
                ) shopUserDTO.setDefaultBillingAddressId(
                    optional.get().getDefaultBillingAddress().getId()
                );
                if (
                    !Objects.isNull(optional.get().getDefaultDeliveryAddress())
                ) shopUserDTO.setDefaultDeliveryAddressId(
                    optional.get().getDefaultDeliveryAddress().getId()
                );
                return new ResponseEntity<>(shopUserDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            Optional<ShopUser> optional = shopUserDao.findById(id);
            if (optional.isPresent()) {
                if (!Objects.isNull(cartDao.findByUserId(id))) cartDao.deleteById(
                    cartDao.findByUserId(id).getId()
                );
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

    public ResponseEntity<ShopUserDTO> updateUser(Integer id, ShopUserDTO shopUserDTO) {
        try {
            if (!Objects.isNull(shopUserDTO)) {
                ShopUser shopUser = shopUserDao.findById(id).get();
                if (!shopUserDTO.getFirstName().equals("")) shopUser.setFirstName(
                    shopUserDTO.getFirstName()
                );
                if (!shopUserDTO.getLastName().equals("")) shopUser.setLastName(
                    shopUserDTO.getLastName()
                );
                if (!shopUserDTO.getPhoneNumber().equals("")) shopUser.setPhoneNumber(
                    shopUserDTO.getPhoneNumber()
                );
                shopUserDao.save(shopUser);
                return new ResponseEntity<>(shopUserDTOMapper.toDTO(shopUser), HttpStatus.OK);
            } else return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ShopUserDTO> updateBillingAddress(Integer userId, Integer addressId) {
        try {
            Optional<ShopUser> shopUserOptional = shopUserDao.findById(userId);
            Optional<Address> addressOptional = addressDao.findById(addressId);
            if (shopUserOptional.isPresent() && addressOptional.isPresent()) {
                ShopUser shopUser = shopUserOptional.get();
                Address address = addressOptional.get();
                shopUser.setDefaultBillingAddress(address);
                ShopUserDTO shopUserDTO = shopUserDTOMapper.toDTO(shopUserDao.save(shopUser));
                shopUserDTO.setDefaultBillingAddressId(shopUser.getDefaultBillingAddress().getId());
                if (!Objects.isNull(shopUser.getDefaultDeliveryAddress())) {
                    shopUserDTO.setDefaultDeliveryAddressId(
                        shopUser.getDefaultDeliveryAddress().getId()
                    );
                }
                return new ResponseEntity<>(shopUserDTO, HttpStatus.OK);
            } else return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ShopUserDTO> updateDeliveryAddress(Integer userId, Integer addressId) {
        try {
            Optional<ShopUser> shopUserOptional = shopUserDao.findById(userId);
            Optional<Address> addressOptional = addressDao.findById(addressId);
            if (shopUserOptional.isPresent() && addressOptional.isPresent()) {
                ShopUser shopUser = shopUserOptional.get();
                Address address = addressOptional.get();
                shopUser.setDefaultDeliveryAddress(address);
                ShopUserDTO shopUserDTO = shopUserDTOMapper.toDTO(shopUserDao.save(shopUser));
                shopUserDTO.setDefaultDeliveryAddressId(
                    shopUser.getDefaultDeliveryAddress().getId()
                );
                if (!Objects.isNull(shopUser.getDefaultBillingAddress())) {
                    shopUserDTO.setDefaultBillingAddressId(
                        shopUser.getDefaultBillingAddress().getId()
                    );
                }
                return new ResponseEntity<>(shopUserDTO, HttpStatus.OK);
            } else return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ShopUserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> forgotPassword(String email) {
        try {
            ShopUser shopUser = shopUserDao.findByEmail(email);
            if (!Objects.isNull(shopUser)) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("ddshop650@gmail.com");
                message.setTo(email);
                message.setSubject("DDShop Change Password");
                message.setText(
                    "To change your password, please follow the following link : " +
                    "http://localhost:3000/change_password/" +
                    shopUser.getId()
                );
                javaMailSender.send(message);
                return new ResponseEntity<>("Password change email sent.", HttpStatus.OK);
            } else return new ResponseEntity<>(
                "No account with specified email.",
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> changePassword(Integer id, String newPassword) {
        try {
            Optional<ShopUser> shopUserOptional = shopUserDao.findById(id);
            if (shopUserOptional.isPresent()) {
                ShopUser shopUser = shopUserOptional.get();
                shopUser.setPassword(DDShopUtils.encodePasswordMD5(newPassword));
                shopUserDao.save(shopUser);
                return new ResponseEntity<>("Successfully changed password.", HttpStatus.OK);
            } else return new ResponseEntity<>(
                "No account with specified email.",
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
