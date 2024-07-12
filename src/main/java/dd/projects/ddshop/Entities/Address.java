package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "street_line")
    private String streetLine;
    @Column(name = "postal_code")
    private Integer postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "county")
    private String county;
    @Column(name = "country")
    private String country;
}
