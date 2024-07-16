package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.name=:name")
    Product findByName(@Param("name") String name);
}
