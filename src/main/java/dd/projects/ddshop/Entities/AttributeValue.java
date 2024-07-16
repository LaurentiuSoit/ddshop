package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "attribute_value")
public class AttributeValue implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "val")
    private String value;
}
