package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ShopUserDTO;
import dd.projects.ddshop.Entities.ShopUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopUserDTOMapper {
    ShopUserDTO toDTO(ShopUser shopUser);

    ShopUser toEntity(ShopUserDTO shopUserDTO);
}
