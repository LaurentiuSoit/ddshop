package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.AttributeValueDTO;
import dd.projects.ddshop.Services.AttributeValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/attribute_value")
public class AttributeValueController {

    AttributeValueService attributeValueService;

    public AttributeValueController(AttributeValueService attributeValueService) {
        this.attributeValueService = attributeValueService;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<AttributeValueDTO> getAttributeValueById(@PathVariable Integer id) {
        try {
            return attributeValueService.getAttributeValueById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AttributeValueDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
