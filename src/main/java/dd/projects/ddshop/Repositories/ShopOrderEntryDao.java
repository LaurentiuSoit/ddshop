package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ShopOrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderEntryDao extends JpaRepository<ShopOrderEntry, Integer> {}
