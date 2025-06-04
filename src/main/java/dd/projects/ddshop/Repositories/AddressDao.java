package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressDao extends JpaRepository<Address, Integer> {
    @Query(value = "select * from address where shop_user_id = :id", nativeQuery = true)
    List<Address> findByShopUserId(@Param("id") Integer id);
}
