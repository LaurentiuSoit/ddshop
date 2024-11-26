package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.CartEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartEntryDao extends JpaRepository<CartEntry, Integer> {
    @Query("select ce from CartEntry ce where ce.cart.id=:id")
    List<CartEntry> getEntriesByCart(@Param("id") Integer id);
}
