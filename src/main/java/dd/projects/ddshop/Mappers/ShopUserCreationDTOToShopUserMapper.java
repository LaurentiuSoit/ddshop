package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ShopUserCreationDTO;
import dd.projects.ddshop.Entities.ShopUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopUserCreationDTOToShopUserMapper {
    ShopUser toEntity(ShopUserCreationDTO shopUserCreationDTO);
}
