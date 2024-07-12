package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopUserDao extends JpaRepository<ShopUser, Integer> {
}
