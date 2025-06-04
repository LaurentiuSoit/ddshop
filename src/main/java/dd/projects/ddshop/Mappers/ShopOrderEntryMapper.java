package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ShopOrderEntryDTO;
import dd.projects.ddshop.Entities.ShopOrderEntry;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopOrderEntryMapper {
    ShopOrderEntryDTO toDTO(ShopOrderEntry shopOrderEntry);

    List<ShopOrderEntryDTO> toDTOList(List<ShopOrderEntry> shopOrderEntries);
}
