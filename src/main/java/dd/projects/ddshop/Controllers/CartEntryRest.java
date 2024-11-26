package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.CartEntryDTO;
import dd.projects.ddshop.Services.CartEntryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cartEntry")
public class CartEntryRest {

    CartEntryService cartEntryService;

    public CartEntryRest(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<CartEntryDTO>> getEntriesByCart(@PathVariable Integer id) {
        try {
            return cartEntryService.getEntriesByCart(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<List<CartEntryDTO>> updateQuantity(
        @RequestParam Integer entryId,
        @RequestParam Integer cartId,
        @RequestParam Integer newQuantity
    ) {
        try {
            return cartEntryService.updateQuantity(entryId, cartId, newQuantity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<List<CartEntryDTO>> deleteCartEntry(
        @RequestParam Integer entryId,
        @RequestParam Integer cartId
    ) {
        try {
            return cartEntryService.deleteCartEntry(entryId, cartId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
