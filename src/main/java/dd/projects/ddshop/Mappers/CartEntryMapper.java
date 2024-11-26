package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.CartEntryDTO;
import dd.projects.ddshop.Entities.CartEntry;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartEntryMapper {
    List<CartEntryDTO> toDTOList(List<CartEntry> cartEntryList);
}
