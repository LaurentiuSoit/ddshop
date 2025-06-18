package dd.projects.ddshop.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class ProductFilterCriteriaDTO {

    private boolean inStock;
    private Integer minPrice;
    private Integer maxPrice;
    private String name;
    private Integer categoryId;
    private List<Integer> attributeValueIds;
}
