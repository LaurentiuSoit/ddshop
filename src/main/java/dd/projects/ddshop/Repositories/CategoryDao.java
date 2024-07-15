package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findByName(@Param("name") String name);
}
