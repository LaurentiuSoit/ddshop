package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.CartDTO;
import dd.projects.ddshop.Services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cart")
public class CartRest {

    CartService cartService;

    public CartRest(CartService cartService) {
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
}
