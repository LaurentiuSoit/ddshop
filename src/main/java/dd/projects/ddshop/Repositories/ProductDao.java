package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
