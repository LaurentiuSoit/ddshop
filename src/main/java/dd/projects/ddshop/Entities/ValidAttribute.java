package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "valid_attribute")
public class ValidAttribute implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @OneToOne
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeValue;
}
