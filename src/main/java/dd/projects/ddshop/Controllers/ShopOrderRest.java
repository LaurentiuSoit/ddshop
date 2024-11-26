package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ShopOrderDTO;
import dd.projects.ddshop.Services.ShopOrderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
public class ShopOrderRest {

    ShopOrderService shopOrderService;

    public ShopOrderRest(ShopOrderService shopOrderService) {
        this.shopOrderService = shopOrderService;
    }

    @PostMapping(path = "/place")
    public ResponseEntity<String> placeOrder(@RequestBody ShopOrderDTO shopOrderDTO) {
        try {
            return shopOrderService.placeOrder(shopOrderDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<ShopOrderDTO>> getOrdersByUser(@PathVariable Integer id) {
        try {
            return shopOrderService.getOrdersByUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
