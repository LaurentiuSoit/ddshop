package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart, Integer> {}
