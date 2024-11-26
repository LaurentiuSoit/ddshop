package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.CartEntryDTO;
import dd.projects.ddshop.Entities.Cart;
import dd.projects.ddshop.Entities.CartEntry;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Mappers.CartEntryMapper;
import dd.projects.ddshop.Repositories.CartDao;
import dd.projects.ddshop.Repositories.CartEntryDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartEntryService {

    CartEntryDao cartEntryDao;
    CartEntryMapper cartEntryMapper;
    CartDao cartDao;

    public CartEntryService(
        CartEntryDao cartEntryDao,
        CartEntryMapper cartEntryMapper,
        CartDao cartDao
    ) {
        this.cartEntryDao = cartEntryDao;
        this.cartEntryMapper = cartEntryMapper;
        this.cartDao = cartDao;
    }

    public CartEntry createCartEntry(Product product, Integer quantity) {
        try {
            CartEntry cartEntry = new CartEntry();
            cartEntry.setProduct(product);
            cartEntry.setQuantity(quantity);
            cartEntry.setPricePerPiece(product.getPrice());
            cartEntry.setTotalPricePerEntry(cartEntry.getQuantity() * cartEntry.getPricePerPiece());
            return cartEntry;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new CartEntry();
    }

    public ResponseEntity<List<CartEntryDTO>> deleteCartEntry(Integer entryId, Integer cartId) {
        try {
            Optional<CartEntry> optionalCartEntry = cartEntryDao.findById(entryId);
            Optional<Cart> optionalCart = cartDao.findById(cartId);
            if (optionalCartEntry.isPresent() && optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                CartEntry cartEntry = optionalCartEntry.get();
                cart.setTotalPrice(cart.getTotalPrice() - cartEntry.getTotalPricePerEntry());
                if (cart.getTotalPrice() < 0) cart.setTotalPrice(0);
                cartEntryDao.deleteById(entryId);
                cartDao.save(cart);
                return getEntriesByCart(cartId);
            } else return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CartEntryDTO>> updateQuantity(
        Integer entryId,
        Integer cartId,
        Integer newQuantity
    ) {
        try {
            Optional<CartEntry> optionalCartEntry = cartEntryDao.findById(entryId);
            Optional<Cart> optionalCart = cartDao.findById(cartId);
            if (optionalCartEntry.isPresent() && optionalCart.isPresent()) {
                CartEntry cartEntry = optionalCartEntry.get();
                Cart cart = optionalCart.get();
                if (newQuantity == 0) {
                    return deleteCartEntry(entryId, cartId);
                } else if (newQuantity <= cartEntry.getProduct().getAvailableQuantity()) {
                    cart.setTotalPrice(cart.getTotalPrice() - cartEntry.getTotalPricePerEntry());
                    cartEntry.setQuantity(newQuantity);
                    cartEntry.setTotalPricePerEntry(
                        cartEntry.getQuantity() * cartEntry.getPricePerPiece()
                    );
                    cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getTotalPricePerEntry());
                    cartEntryDao.save(cartEntry);
                    cartDao.save(cart);
                    return getEntriesByCart(cartId);
                } else return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CartEntryDTO>> getEntriesByCart(Integer id) {
        try {
            List<CartEntry> cartEntryList = cartEntryDao.getEntriesByCart(id);
            List<CartEntryDTO> cartEntryDTOList = cartEntryMapper.toDTOList(cartEntryList);
            for (CartEntryDTO cartEntryDTO : cartEntryDTOList) {
                cartEntryDTOAssignFKIds(
                    cartEntryDTO,
                    cartEntryDao.findById(cartEntryDTO.getId()).get()
                );
            }
            return new ResponseEntity<>(cartEntryDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void cartEntryDTOAssignFKIds(CartEntryDTO cartEntryDTO, CartEntry cartEntry) {
        cartEntryDTO.setCartId(cartEntry.getCart().getId());
        cartEntryDTO.setProductId(cartEntry.getProduct().getId());
    }
}
