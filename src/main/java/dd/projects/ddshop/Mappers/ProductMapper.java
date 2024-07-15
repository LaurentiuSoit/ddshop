package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ProductDTO;
import dd.projects.ddshop.Entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOList(List<Product> productList);
}
