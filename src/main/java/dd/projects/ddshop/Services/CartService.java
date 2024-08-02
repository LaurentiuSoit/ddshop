package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.CartDTO;
import dd.projects.ddshop.Entities.Cart;
import dd.projects.ddshop.Entities.CartEntry;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Exceptions.UserNotFoundException;
import dd.projects.ddshop.Mappers.CartMapper;
import dd.projects.ddshop.Repositories.CartDao;
import dd.projects.ddshop.Repositories.ShopUserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    CartDao cartDao;
    CartMapper cartMapper;
    ShopUserDao shopUserDao;

    public CartService(CartDao cartDao, CartMapper cartMapper, ShopUserDao shopUserDao) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
        this.shopUserDao = shopUserDao;
    }

    public ResponseEntity<CartDTO> getCartByUserId(Integer id) {
        try {
            Cart cart = cartDao.findByUserId(id);
            if (!Objects.isNull(cart)) {
                CartDTO cartDTO = cartMapper.toDTO(cart);
                cartDTOAssignFKIds(cartDTO, cart);
                return new ResponseEntity<>(cartDTO, HttpStatus.OK);
            } else {
                cart = createNewCart(id);
                CartDTO cartDTO = cartMapper.toDTO(cart);
                cartDTOAssignFKIds(cartDTO, cart);
                return new ResponseEntity<>(cartDTO, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new CartDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Cart createNewCart(Integer id) {
        Cart cart;
        cart = new Cart();
        Optional<ShopUser> shopUserOptional = shopUserDao.findById(id);
        if (shopUserOptional.isPresent()) {
            cart.setShopUser(shopUserOptional.get());
            cart.setTotalPrice(0);
            cart.setCartEntryList(new ArrayList<>());
            cartDao.save(cart);
            return cart;
        } else throw new UserNotFoundException("User not found.");
    }

    private void cartDTOAssignFKIds(CartDTO cartDTO, Cart cart) {
        cartDTO.setShopUserId(cart.getShopUser().getId());
        List<Integer> cartEntryIdList = new ArrayList<>();
        for (CartEntry cartEntry : cart.getCartEntryList()) {
            cartEntryIdList.add(cartEntry.getId());
        }
        cartDTO.setCartEntryIdList(cartEntryIdList);
    }
}
