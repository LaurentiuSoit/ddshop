package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopUserDao extends JpaRepository<ShopUser, Integer> {
    @Query("select u from ShopUser u where u.email=:email")
    ShopUser findByEmail(@Param("email") String email);
}
