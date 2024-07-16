package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_entry")
public class CartEntry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_per_piece")
    private Integer pricePerPiece;

    @Column(name = "total_price_per_entry")
    private Integer totalPricePerEntry;
}
