package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.CartDTO;
import dd.projects.ddshop.Services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Integer id) {
        try {
            return cartService.getCartByUserId(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new CartDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addProductToCart(
        @RequestParam Integer cartId,
        @RequestParam Integer productId,
        @RequestParam Integer quantity
    ) {
        try {
            return cartService.addProductToCart(cartId, productId, quantity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
