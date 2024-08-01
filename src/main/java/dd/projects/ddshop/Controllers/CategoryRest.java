package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Services.CategoryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/category")
public class CategoryRest {

    CategoryService categoryService;

    public CategoryRest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            return categoryService.addCategory(categoryDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        try {
            return categoryService.getCategoryById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new CategoryDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
