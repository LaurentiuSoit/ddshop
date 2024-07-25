package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Services.CategoryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/category")
public class CategoryRest {

    CategoryService categoryService;

    public CategoryRest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
