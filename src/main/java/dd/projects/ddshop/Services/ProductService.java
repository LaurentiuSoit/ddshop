package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Entities.Category;
import dd.projects.ddshop.Entities.Product;
import dd.projects.ddshop.Entities.ValidAttribute;
import dd.projects.ddshop.Mappers.ProductMapper;
import dd.projects.ddshop.Repositories.CategoryDao;
import dd.projects.ddshop.Repositories.ProductDao;
import dd.projects.ddshop.Repositories.ValidAttributeDao;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductDao productDao;
    CategoryDao categoryDao;
    ValidAttributeDao validAttributeDao;
    ProductMapper productMapper;

    public ProductService(
        ProductDao productDao,
        CategoryDao categoryDao,
        ProductMapper productMapper,
        ValidAttributeDao validAttributeDao
    ) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.productMapper = productMapper;
        this.validAttributeDao = validAttributeDao;
    }

    public ResponseEntity<String> addProduct(ProductDTO productDTO) {
        try {
            if (!Objects.isNull(productDTO)) {
                Product product = productMapper.toEntity(productDTO);
                product.setAddedDate(LocalDate.now());
                Optional<Category> categoryOptional = categoryDao.findById(
                    productDTO.getCategoryId()
                );
                if (categoryOptional.isPresent()) {
                    product.setCategory(categoryOptional.get());
                } else {
                    return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
                }
                List<ValidAttribute> validAttributeList = new ArrayList<>();
                for (Integer validAttributeId : productDTO.getValidAttributeIdList()) {
                    Optional<ValidAttribute> validAttributeOptional = validAttributeDao.findById(
                        validAttributeId
                    );
                    if (validAttributeOptional.isPresent()) {
                        validAttributeList.add(validAttributeOptional.get());
                    } else {
                        return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
                    }
                }
                product.setValidAttributeList(validAttributeList);
                productDao.save(product);
                if (product.getId() == null) {
                    return new ResponseEntity<>("Product added successfully.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Product updated successfully.", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ProductDTO> getProductById(Integer id) {
        try {
            Optional<Product> productOptional = productDao.findById(id);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                ProductDTO productDTO = productMapper.toDTO(product);
                productDTOAssignFKIds(productDTO, product);
                return new ResponseEntity<>(productDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ProductDTO(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
        Integer categoryId,
        String sortBy
    ) {
        try {
            List<ProductDTO> productDTOList = productMapper.toDTOList(
                productDao.findByCategory(categoryId)
            );
            for (ProductDTO productDTO : productDTOList) {
                productDTOAssignFKIds(productDTO, productDao.findById(productDTO.getId()).get());
            }
            return sortedProductList(sortBy, productDTOList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<List<ProductDTO>> sortedProductList(
        String sortBy,
        List<ProductDTO> productDTOList
    ) {
        Comparator<ProductDTO> productDTOComparator;
        switch (sortBy.toLowerCase()) {
            case "newest" -> {
                productDTOComparator = Comparator.comparing(ProductDTO::getId);
                productDTOComparator = productDTOComparator.reversed();
            }
            case "priceasc" -> productDTOComparator = Comparator.comparing(ProductDTO::getPrice);
            case "pricedesc" -> {
                productDTOComparator = Comparator.comparing(ProductDTO::getPrice);
                productDTOComparator = productDTOComparator.reversed();
            }
            default -> {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(
            productDTOList.stream().sorted(productDTOComparator).collect(Collectors.toList()),
            HttpStatus.OK
        );
    }

    public ResponseEntity<List<ProductDTO>> getAllProducts(String sortBy) {
        try {
            List<ProductDTO> productDTOList = productMapper.toDTOList(productDao.findAll());
            for (ProductDTO productDTO : productDTOList) {
                productDTOAssignFKIds(productDTO, productDao.findById(productDTO.getId()).get());
            }
            return sortedProductList(sortBy, productDTOList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            Optional<Product> optional = productDao.findById(id);
            if (optional.isPresent()) {
                productDao.deleteById(id);
                return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User does not exist.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void productDTOAssignFKIds(ProductDTO productDTO, Product product) {
        productDTO.setCategoryId(product.getCategory().getId());
        List<Integer> validAttributeIdList = new ArrayList<>();
        for (ValidAttribute validAttribute : product.getValidAttributeList()) {
            validAttributeIdList.add(validAttribute.getId());
        }
        productDTO.setValidAttributeIdList(validAttributeIdList);
    }
}
