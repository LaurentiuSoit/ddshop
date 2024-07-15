package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductRest {
    @Autowired
    ProductService productService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProductsSortedBy(String sortBy) {
        try {
            return productService.getAllProductsSortedBy(sortBy);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<ProductDTO>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
