package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ValidAttributeDTO;
import dd.projects.ddshop.DTOs.ValidAttributeValueDTO;
import dd.projects.ddshop.Entities.ValidAttribute;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidAttributeMapper {
    ValidAttribute toEntity(ValidAttributeValueDTO validAttributeValueDTO);

    ValidAttributeDTO toDTO(ValidAttribute validAttribute);

    List<ValidAttributeDTO> toDTOList(List<ValidAttribute> validAttributeList);
}
