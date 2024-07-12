package dd.projects.ddshop.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
@Data
@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "shop_user_id")
    private ShopUser shopUser;
    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<CartEntry> cartEntryList;
    @Column(name = "total_price")
    private Integer totalPrice;
}
