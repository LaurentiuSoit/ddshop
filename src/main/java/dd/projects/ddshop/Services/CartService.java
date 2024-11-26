package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.CartDTO;
import dd.projects.ddshop.Entities.Cart;
import dd.projects.ddshop.Entities.CartEntry;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Entities.ShopUser;
import dd.projects.ddshop.Exceptions.UserNotFoundException;
import dd.projects.ddshop.Mappers.CartMapper;
import dd.projects.ddshop.Repositories.CartDao;
import dd.projects.ddshop.Repositories.CartEntryDao;
import dd.projects.ddshop.Repositories.ProductDao;
import dd.projects.ddshop.Repositories.ShopUserDao;
import jakarta.transaction.Transactional;
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

    ProductDao productDao;
    CartEntryService cartEntryService;

    CartEntryDao cartEntryDao;

    public CartService(
        CartDao cartDao,
        CartMapper cartMapper,
        ShopUserDao shopUserDao,
        ProductDao productDao,
        CartEntryService cartEntryService,
        CartEntryDao cartEntryDao
    ) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
        this.shopUserDao = shopUserDao;
        this.productDao = productDao;
        this.cartEntryService = cartEntryService;
        this.cartEntryDao = cartEntryDao;
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

    @Transactional
    public ResponseEntity<String> addProductToCart(
        Integer cartId,
        Integer productId,
        Integer quantity
    ) {
        try {
            Optional<Cart> optionalCart = cartDao.findById(cartId);
            if (optionalCart.isPresent()) {
                Optional<Product> optionalProduct = productDao.findById(productId);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    Cart cart = optionalCart.get();
                    for (CartEntry cartEntry : cart.getCartEntryList()) {
                        if (cartEntry.getProduct().equals(product)) {
                            if (
                                cartEntry.getQuantity() + quantity <= product.getAvailableQuantity()
                            ) {
                                updateExistingEntry(quantity, cart, cartEntry);
                                return new ResponseEntity<>(
                                    "Updated Cart Entry Successfully.",
                                    HttpStatus.OK
                                );
                            } else return new ResponseEntity<>(
                                "Not enough products available.",
                                HttpStatus.BAD_REQUEST
                            );
                        }
                    }
                    addNewEntry(quantity, product, cart);
                    return new ResponseEntity<>("Product added to cart.", HttpStatus.OK);
                } else return new ResponseEntity<>(
                    "Product could not be found.",
                    HttpStatus.BAD_REQUEST
                );
            } else return new ResponseEntity<>("Cart could not be found.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void updateExistingEntry(Integer quantity, Cart cart, CartEntry cartEntry) {
        cart.setTotalPrice(cart.getTotalPrice() - cartEntry.getTotalPricePerEntry());
        cartEntry.setQuantity(cartEntry.getQuantity() + quantity);
        cartEntry.setTotalPricePerEntry(cartEntry.getQuantity() * cartEntry.getPricePerPiece());
        cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getTotalPricePerEntry());
        cartEntryDao.save(cartEntry);
        cartDao.save(cart);
    }

    private void addNewEntry(Integer quantity, Product product, Cart cart) {
        CartEntry cartEntry = cartEntryService.createCartEntry(product, quantity);
        cartEntry.setCart(cart);
        cart.getCartEntryList().add(cartEntry);
        cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getTotalPricePerEntry());
        cartEntryDao.save(cartEntry);
        cartDao.save(cart);
    }
}
