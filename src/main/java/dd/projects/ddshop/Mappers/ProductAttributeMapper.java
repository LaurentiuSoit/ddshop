package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ProductAttributeDTO;
import dd.projects.ddshop.Entities.ProductAttribute;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttribute toEntity(ProductAttributeDTO productAttributeDTO);

    ProductAttributeDTO toDTO(ProductAttribute productAttribute);

    List<ProductAttributeDTO> toDTOList(List<ProductAttribute> productAttributeList);
}
