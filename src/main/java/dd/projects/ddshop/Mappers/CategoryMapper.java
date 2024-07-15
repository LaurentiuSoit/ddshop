package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.CategoryDTO;
import dd.projects.ddshop.Entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTOList(List<Category> categoryList);
}
