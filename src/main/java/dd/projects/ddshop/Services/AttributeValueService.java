package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.AttributeValueDTO;
import dd.projects.ddshop.Entities.AttributeValue;
import dd.projects.ddshop.Mappers.AttributeValueMapper;
import dd.projects.ddshop.Repositories.AttributeValueDao;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AttributeValueService {

    AttributeValueDao attributeValueDao;
    AttributeValueMapper attributeValueMapper;

    public AttributeValueService(
        AttributeValueDao attributeValueDao,
        AttributeValueMapper attributeValueMapper
    ) {
        this.attributeValueDao = attributeValueDao;
        this.attributeValueMapper = attributeValueMapper;
    }

    public ResponseEntity<AttributeValueDTO> getAttributeValueById(Integer id) {
        try {
            Optional<AttributeValue> attributeValueOptional = attributeValueDao.findById(id);
            if (attributeValueOptional.isPresent()) {
                return new ResponseEntity<>(
                    attributeValueMapper.toDTO(attributeValueOptional.get()),
                    HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(new AttributeValueDTO(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AttributeValueDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
