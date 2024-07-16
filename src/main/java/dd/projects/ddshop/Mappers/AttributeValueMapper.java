package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.AttributeValueDTO;
import dd.projects.ddshop.Entities.AttributeValue;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeValueMapper {
    AttributeValue toEntity(AttributeValueDTO attributeValueDTO);

    AttributeValueDTO toDTO(AttributeValue attributeValue);

    List<AttributeValueDTO> toDTOList(List<AttributeValue> attributeValueList);
}
