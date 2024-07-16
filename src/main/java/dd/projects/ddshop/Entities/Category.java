package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_description")
    private String description;
}
