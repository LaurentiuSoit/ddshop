package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.ProductAttributeDTO;
import dd.projects.ddshop.Entities.AttributeValue;
import dd.projects.ddshop.Entities.ProductAttribute;
import dd.projects.ddshop.Mappers.ProductAttributeMapper;
import dd.projects.ddshop.Repositories.ProductAttributeDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeService {

    ProductAttributeDao productAttributeDao;
    ProductAttributeMapper productAttributeMapper;

    public ProductAttributeService(
        ProductAttributeDao productAttributeDao,
        ProductAttributeMapper productAttributeMapper
    ) {
        this.productAttributeDao = productAttributeDao;
        this.productAttributeMapper = productAttributeMapper;
    }

    public ResponseEntity<List<ProductAttributeDTO>> getAllProductAttributes() {
        try {
            List<ProductAttributeDTO> productAttributeDTOList = productAttributeMapper.toDTOList(
                productAttributeDao.findAll()
            );
            for (ProductAttributeDTO productAttributeDTO : productAttributeDTOList) {
                List<Integer> idList = new ArrayList<>();
                ProductAttribute productAttribute = productAttributeDao
                    .findById(productAttributeDTO.getId())
                    .get();
                for (AttributeValue attributeValue : productAttribute.getAttributeValueList()) {
                    idList.add(attributeValue.getId());
                }
                productAttributeDTO.setAttributeValueIdList(idList);
            }
            return new ResponseEntity<>(productAttributeDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
