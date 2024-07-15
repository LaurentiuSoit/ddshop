package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Mappers.ProductMapper;
import dd.projects.ddshop.Repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    ProductMapper productMapper;

    public ResponseEntity<String> addProduct(ProductDTO productDTO) {
        try {
            if (!Objects.isNull(productDTO)) {
                Product product = productMapper.toEntity(productDTO);
                productDao.save(product);
                return new ResponseEntity<String>("Product added successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> productDTOList = productMapper.toDTOList(
                    productDao.findAll(Sort.by(Sort.Direction.DESC, "addedDate")));
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            Optional optional = productDao.findById(id);
            if (optional.isPresent()) {
                productDao.deleteById(id);
                return new ResponseEntity<String>("User deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("User does not exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
