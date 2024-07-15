package dd.projects.ddshop.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ProductAttributeDTO {
    private String name;
    private List<AttributeValueDTO> attributeValueList;
}
