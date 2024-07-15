package dd.projects.ddshop.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ValidAttributeDTO {
    private ProductAttributeDTO productAttribute;
    private List<ValidAttributeValueDTO> validAttributeValueList;
}
