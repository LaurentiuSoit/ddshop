package dd.projects.ddshop.DTOs;

import dd.projects.ddshop.Entities.ShopOrder;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ShopOrderDTO {

    private Integer id;
    private Integer shopUserId;
    private Integer cartId;
    private ShopOrder.paymentType paymentType;
    private Integer deliveryAddressId;
    private Integer invoiceAddressId;
    private Integer totalPrice;
    private LocalDate orderDate;
}
