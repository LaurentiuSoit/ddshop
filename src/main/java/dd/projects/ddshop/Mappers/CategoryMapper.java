package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Entities.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTOList(List<Category> categoryList);
}
