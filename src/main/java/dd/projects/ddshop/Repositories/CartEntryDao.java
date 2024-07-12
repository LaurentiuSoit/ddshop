package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntryDao extends JpaRepository<CartEntry, Integer> {
}
