package dd.projects.ddshop.DTOs;

import lombok.Data;

@Data
public class CartEntryDTO {

    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private Integer pricePerPiece;
    private Integer totalPricePerEntry;
}
