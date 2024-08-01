package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Entities.Category;
import dd.projects.ddshop.Mappers.CategoryMapper;
import dd.projects.ddshop.Repositories.CategoryDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    CategoryDao categoryDao;

    CategoryMapper categoryMapper;

    public CategoryService(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    public ResponseEntity<String> addCategory(CategoryDTO categoryDTO) {
        try {
            if (!Objects.isNull(categoryDao)) {
                Category category = categoryMapper.toEntity(categoryDTO);
                if (Objects.isNull(categoryDao.findByName(category.getName()))) {
                    categoryDao.save(category);
                    return new ResponseEntity<>("Successfully added category.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Category already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Bad Request.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOList = categoryMapper.toDTOList(categoryDao.findAll());
            return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<CategoryDTO> getCategoryById(Integer id) {
        try {
            Optional<Category> categoryOptional = categoryDao.findById(id);
            if (categoryOptional.isPresent()) {
                return new ResponseEntity<>(
                    categoryMapper.toDTO(categoryOptional.get()),
                    HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(new CategoryDTO(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new CategoryDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
