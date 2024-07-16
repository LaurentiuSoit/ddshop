package dd.projects.ddshop.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class ValidAttributeDTO {

    private Integer id;
    private Integer productAttributeId;
    private List<Integer> validAttributeValueIdList;
}
