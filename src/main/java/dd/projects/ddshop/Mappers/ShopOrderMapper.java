package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ShopOrderDTO;
import dd.projects.ddshop.Entities.ShopOrder;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopOrderMapper {
    ShopOrder toEntity(ShopOrderDTO shopOrderDTO);

    ShopOrderDTO toDTO(ShopOrder shopOrder);

    List<ShopOrderDTO> toDTOList(List<ShopOrder> shopOrderList);
}
