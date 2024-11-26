package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ShopOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopOrderDao extends JpaRepository<ShopOrder, Integer> {
    @Query("select so from ShopOrder so where so.shopUser.id=:id")
    List<ShopOrder> getOrdersByUser(@Param("id") Integer id);
}
