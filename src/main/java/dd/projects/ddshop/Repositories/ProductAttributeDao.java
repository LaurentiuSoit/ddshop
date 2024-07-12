package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeDao extends JpaRepository<ProductAttribute, Integer> {
}
