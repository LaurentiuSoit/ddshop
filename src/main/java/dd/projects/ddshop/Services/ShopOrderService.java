package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ShopOrderDTO;
import dd.projects.ddshop.DTOs.ShopOrderEntryDTO;
import dd.projects.ddshop.Entities.*;
import dd.projects.ddshop.Exceptions.InsufficientQuantityException;
import dd.projects.ddshop.Mappers.ShopOrderEntryMapper;
import dd.projects.ddshop.Mappers.ShopOrderMapper;
import dd.projects.ddshop.Repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ShopOrderService {

    ShopOrderDao shopOrderDao;
    ShopOrderMapper shopOrderMapper;
    JavaMailSender javaMailSender;
    ShopUserDao shopUserDao;
    CartDao cartDao;
    AddressDao addressDao;
    CartEntryService cartEntryService;
    EntityManager entityManager;
    ProductDao productDao;
    ShopOrderEntryDao shopOrderEntryDao;
    ShopOrderEntryMapper shopOrderEntryMapper;

    public ShopOrderService(
        ShopOrderDao shopOrderDao,
        ShopOrderMapper shopOrderMapper,
        JavaMailSender javaMailSender,
        ShopUserDao shopUserDao,
        CartDao cartDao,
        AddressDao addressDao,
        CartEntryService cartEntryService,
        EntityManager entityManager,
        ProductDao productDao,
        ShopOrderEntryDao shopOrderEntryDao,
        ShopOrderEntryMapper shopOrderEntryMapper
    ) {
        this.shopOrderDao = shopOrderDao;
        this.shopOrderMapper = shopOrderMapper;
        this.javaMailSender = javaMailSender;
        this.shopUserDao = shopUserDao;
        this.cartDao = cartDao;
        this.addressDao = addressDao;
        this.cartEntryService = cartEntryService;
        this.entityManager = entityManager;
        this.productDao = productDao;
        this.shopOrderEntryDao = shopOrderEntryDao;
        this.shopOrderEntryMapper = shopOrderEntryMapper;
    }

    @Transactional
    public ResponseEntity<String> placeOrder(ShopOrderDTO shopOrderDTO) {
        try {
            if (shopOrderDTO.getTotalPrice() == 0) {
                return new ResponseEntity<>("No products in cart.", HttpStatus.BAD_REQUEST);
            }
            ShopOrder shopOrder = shopOrderMapper.toEntity(shopOrderDTO);
            Optional<ShopUser> shopUserOptional = shopUserDao.findById(
                shopOrderDTO.getShopUserId()
            );
            Optional<Cart> cartOptional = cartDao.findById(shopOrderDTO.getCartId());
            Optional<Address> billingAddressOptional = addressDao.findById(
                shopOrderDTO.getInvoiceAddressId()
            );
            Optional<Address> deliveryAddressOptional = addressDao.findById(
                shopOrderDTO.getDeliveryAddressId()
            );
            if (
                shopUserOptional.isPresent() &&
                cartOptional.isPresent() &&
                billingAddressOptional.isPresent() &&
                deliveryAddressOptional.isPresent()
            ) {
                Cart cart = cartOptional.get();
                ShopUser shopUser = shopUserOptional.get();
                Address billingAddress = billingAddressOptional.get();
                Address deliveryAddress = deliveryAddressOptional.get();

                shopOrder.setCart(cart);
                shopOrder.setShopUser(shopUser);
                shopOrder.setInvoiceAddress(billingAddress);
                shopOrder.setDeliveryAddress(deliveryAddress);
                saveOrderEntries(shopOrder, cart.getCartEntryList());
                shopOrderDao.save(shopOrder);
                Integer orderTotalPrice = cart.getTotalPrice();
                subtractQuantity(cart.getCartEntryList());
                sendEmail(
                    shopUser.getEmail(),
                    shopUser.getFirstName(),
                    shopUser.getLastName(),
                    deliveryAddress,
                    orderTotalPrice,
                    shopOrder.getCart().getCartEntryList()
                );
                clearCart(cart);
                return new ResponseEntity<>("Order placed.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (InsufficientQuantityException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ShopOrderDTO>> getOrdersByUser(Integer id) {
        try {
            List<ShopOrder> shopOrders = shopOrderDao.getOrdersByUser(id);
            List<ShopOrderDTO> shopOrderDTOList = new ArrayList<>();
            for (ShopOrder shopOrder : shopOrders) {
                ShopOrderDTO shopOrderDTO = shopOrderMapper.toDTO(shopOrder);
                shopOrderDTOAssignFKIds(shopOrderDTO, shopOrder);
                shopOrderDTO.setOrderEntries(
                    shopOrderEntryMapper.toDTOList(shopOrder.getShopOrderEntries())
                );
                for (ShopOrderEntryDTO shopOrderEntryDTO : shopOrderDTO.getOrderEntries()) {
                    shopOrderEntryDTOAssignFKIds(
                        shopOrderEntryDTO,
                        shopOrderEntryDao.findById(shopOrderEntryDTO.getId()).get()
                    );
                }
                shopOrderDTOList.add(shopOrderDTO);
            }
            return new ResponseEntity<>(shopOrderDTOList.reversed(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void subtractQuantity(List<CartEntry> cartEntryList) {
        for (CartEntry cartEntry : cartEntryList) {
            Product product = cartEntry.getProduct();
            if (
                cartEntry.getQuantity() > product.getAvailableQuantity()
            ) throw new InsufficientQuantityException(
                "Cart entry quantity bigger than remaining product stock for " +
                product.getName() +
                " . Remaining stock : " +
                product.getAvailableQuantity()
            );
            else {
                product.setAvailableQuantity(
                    product.getAvailableQuantity() - cartEntry.getQuantity()
                );
                productDao.save(product);
            }
        }
    }

    private void saveOrderEntries(ShopOrder shopOrder, List<CartEntry> cartEntryList) {
        for (CartEntry cartEntry : cartEntryList) {
            ShopOrderEntry shopOrderEntry = mapCartEntryToShopOrderEntry(cartEntry);
            shopOrder.getShopOrderEntries().add(shopOrderEntry);
            shopOrderEntryDao.save(shopOrderEntry);
        }
    }

    private ShopOrderEntry mapCartEntryToShopOrderEntry(CartEntry cartEntry) {
        ShopOrderEntry shopOrderEntry = new ShopOrderEntry();
        shopOrderEntry.setProduct(cartEntry.getProduct());
        shopOrderEntry.setQuantity(cartEntry.getQuantity());
        return shopOrderEntry;
    }

    private void clearCart(Cart cart) {
        List<Integer> entryIds = new ArrayList<>();
        for (CartEntry cartEntry : cart.getCartEntryList()) entryIds.add(cartEntry.getId());
        String deleteQuery = "DELETE FROM CartEntry ce WHERE ce.id IN :ids";
        entityManager.createQuery(deleteQuery).setParameter("ids", entryIds).executeUpdate();
        cart.setTotalPrice(0);
        cartDao.save(cart);
    }

    private void sendEmail(
        String email,
        String firstName,
        String lastName,
        Address deliveryAddress,
        Integer orderTotalPrice,
        List<CartEntry> cartEntryList
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ddshop650@gmail.com");
        message.setTo(email);
        message.setSubject("DDShop Order Confirmation");
        String emailMessage =
            "Your order has been registered and will be delivered as soon as possible." +
            " Total price is $" +
            orderTotalPrice +
            ".\nDelivery Address: " +
            firstName +
            "," +
            lastName +
            ", " +
            deliveryAddress.getStreetLine() +
            ", " +
            deliveryAddress.getCity() +
            ", " +
            deliveryAddress.getPostalCode() +
            ", " +
            deliveryAddress.getCounty() +
            ", " +
            deliveryAddress.getCountry() +
            ".\n";
        emailMessage += "The products you ordered:\n";
        for (CartEntry cartEntry : cartEntryList) {
            emailMessage +=
            cartEntry.getProduct().getName() +
            ": " +
            cartEntry.getQuantity() +
            ", $" +
            cartEntry.getTotalPricePerEntry() +
            "\n";
        }
        message.setText(emailMessage);
        javaMailSender.send(message);
    }

    private void shopOrderDTOAssignFKIds(ShopOrderDTO shopOrderDTO, ShopOrder shopOrder) {
        shopOrderDTO.setShopUserId(shopOrder.getShopUser().getId());
        shopOrderDTO.setCartId(shopOrder.getCart().getId());
        shopOrderDTO.setDeliveryAddressId(shopOrder.getDeliveryAddress().getId());
        shopOrderDTO.setInvoiceAddressId(shopOrder.getInvoiceAddress().getId());
    }

    private void shopOrderEntryDTOAssignFKIds(
        ShopOrderEntryDTO shopOrderEntryDTO,
        ShopOrderEntry shopOrderEntry
    ) {
        shopOrderEntryDTO.setProductId(shopOrderEntry.getProduct().getId());
    }
}
