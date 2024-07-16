package dd.projects.ddshop.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class ProductAttributeDTO {

    private Integer id;
    private String name;
    private List<Integer> attributeValueIdList;
}
