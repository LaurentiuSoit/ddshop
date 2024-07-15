package dd.projects.ddshop.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NamedQuery(name = "Product.findByName", query = "select p from Product p where p.name=:name")
@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_description")
    private String description;
    @Column(name = "price")
    private Integer price;
    @Column(name = "available_quantity")
    private Integer availableQuantity;
    @Column(name = "added_date")
    private LocalDate addedDate;
    @ManyToOne
    private Category category;
    @OneToMany
    @JoinColumn(name="product_id")
    private List<ValidAttribute> validAttributeList;
}
