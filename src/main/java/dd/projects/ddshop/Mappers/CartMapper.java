package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.CartDTO;
import dd.projects.ddshop.Entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
}
