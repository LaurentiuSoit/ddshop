package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findByName(@Param("name") String name);

}
