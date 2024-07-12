package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
}
