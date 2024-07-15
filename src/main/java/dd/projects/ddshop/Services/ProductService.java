package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Mappers.ProductMapper;
import dd.projects.ddshop.Repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public ResponseEntity<List<ProductDTO>> getAllProductsSortedBy(String sortBy) {
        try {
            List<ProductDTO> productDTOList = productMapper.toDTOList(productDao.findAll());
            Comparator<ProductDTO> productDTOComparator;
            switch (sortBy.toLowerCase()) {
                case "newest":
                    productDTOComparator = Comparator.comparing(ProductDTO::getAddedDate);
                    productDTOComparator = productDTOComparator.reversed();
                    break;
                case "price ascending":
                    productDTOComparator = Comparator.comparing(ProductDTO::getPrice);
                    break;
                case "price descending":
                    productDTOComparator = Comparator.comparing(ProductDTO::getPrice);
                    productDTOComparator = productDTOComparator.reversed();
                    break;
                default:
                    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(productDTOList.stream()
                    .sorted(productDTOComparator)
                    .collect(Collectors.toList()), HttpStatus.OK);
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
