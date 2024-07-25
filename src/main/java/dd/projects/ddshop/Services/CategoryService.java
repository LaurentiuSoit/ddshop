package dd.projects.ddshop.Services;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Mappers.CategoryMapper;
import dd.projects.ddshop.Repositories.CategoryDao;
import java.util.ArrayList;
import java.util.List;
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

    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOList = categoryMapper.toDTOList(categoryDao.findAll());
            return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
