package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
    private List<CartEntry> cartEntryList = new ArrayList<>();

    @Column(name = "total_price")
    private Integer totalPrice;
}
