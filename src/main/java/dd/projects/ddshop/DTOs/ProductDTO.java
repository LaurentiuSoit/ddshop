package dd.projects.ddshop.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private Integer price;
    private Integer availableQuantity;
    private LocalDate addedDate;
    private CategoryDTO category;
    private List<ValidAttributeDTO> validAttributeList;
}
