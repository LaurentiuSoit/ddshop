package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "shop_order")
public class ShopOrder implements Serializable {

    public enum paymentType {
        CREDIT_CARD,
        DEBIT_CARD,
        PAYPAL,
        BANK_TRANSFER,
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private ShopUser shopUser;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private paymentType paymentType;

    @OneToOne
    @JoinColumn(name = "delivery_address")
    private Address deliveryAddress;

    @OneToOne
    @JoinColumn(name = "invoice_address")
    private Address invoiceAddress;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @OneToMany
    @JoinColumn(name = "shop_order_id")
    private List<ShopOrderEntry> shopOrderEntries = new ArrayList<>();
}
