package dd.projects.ddshop.Entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "shop_user")
public class ShopUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "default_delivery_address")
    private Address defaultDeliveryAddress;

    @OneToOne
    @JoinColumn(name = "default_billing_address")
    private Address defaultBillingAddress;

    @OneToMany
    @JoinColumn(name = "shop_user_id")
    private List<Address> addressList = new ArrayList<>();
}
