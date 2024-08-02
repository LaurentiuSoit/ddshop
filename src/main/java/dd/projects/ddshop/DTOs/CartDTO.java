package dd.projects.ddshop.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class CartDTO {

    private Integer id;
    private Integer shopUserId;
    private List<Integer> cartEntryIdList;
    private Integer totalPrice;
}
