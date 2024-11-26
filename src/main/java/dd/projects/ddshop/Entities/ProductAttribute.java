package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "product_attribute")
public class ProductAttribute implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_attribute_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "product_attribute_id")
    private List<AttributeValue> attributeValueList = new ArrayList<>();
}
