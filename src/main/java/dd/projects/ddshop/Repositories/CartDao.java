package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartDao extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.shopUser.id=:id")
    Cart findByUserId(@Param("id") Integer id);
}
