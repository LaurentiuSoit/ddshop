package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class ShopOrderEntryDTO {

    private int id;
    private int productId;
    private int quantity;
}
