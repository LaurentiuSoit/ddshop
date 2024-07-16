package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.ValidAttributeValueDTO;
import dd.projects.ddshop.Entities.ValidAttributeValue;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidAttributeValueMapper {
    ValidAttributeValue toEntity(ValidAttributeValueDTO validAttributeValueDTO);

    ValidAttributeValueDTO toDTO(ValidAttributeValue validAttributeValue);

    List<ValidAttributeValueDTO> toDTOList(List<ValidAttributeValue> validAttributeValueList);
}
