package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ValidAttributeDTO;
import dd.projects.ddshop.DTOs.ValidAttributeValueDTO;
import dd.projects.ddshop.Entities.ValidAttribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValidAttributeMapper {
    ValidAttribute toEntity(ValidAttributeValueDTO validAttributeValueDTO);

    ValidAttributeDTO toDTO(ValidAttribute validAttribute);

    List<ValidAttributeDTO> toDTOList(List<ValidAttribute> validAttributeList);
}
