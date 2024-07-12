package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderDao extends JpaRepository<ShopOrder, Integer> {
}
