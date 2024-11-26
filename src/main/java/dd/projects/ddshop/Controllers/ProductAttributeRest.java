package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ProductAttributeDTO;
import dd.projects.ddshop.Services.ProductAttributeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product_attribute")
public class ProductAttributeRest {

    ProductAttributeService productAttributeService;

    public ProductAttributeRest(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<ProductAttributeDTO>> getAllProductAttributes() {
        try {
            return productAttributeService.getAllProductAttributes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
