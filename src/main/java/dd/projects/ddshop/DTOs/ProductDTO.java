package dd.projects.ddshop.DTOs;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer availableQuantity;
    private LocalDate addedDate;
    private Integer categoryId;
    private List<Integer> validAttributeIdList;
}
