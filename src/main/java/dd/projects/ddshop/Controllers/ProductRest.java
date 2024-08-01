package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Services.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductRest {

    ProductService productService;

    public ProductRest(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            return productService.addProduct(productDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            return productService.addProduct(productDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        try {
            return productService.getProductById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getByCategory")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
        @RequestParam Integer categoryId
    ) {
        try {
            return productService.getProductsByCategory(categoryId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProductsSortedBy(@RequestParam String sortBy) {
        try {
            return productService.getAllProductsSortedBy(sortBy);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
