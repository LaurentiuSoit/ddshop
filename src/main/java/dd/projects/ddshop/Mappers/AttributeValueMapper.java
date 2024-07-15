package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.AttributeValueDTO;
import dd.projects.ddshop.Entities.AttributeValue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeValueMapper {
    AttributeValue toEntity(AttributeValueDTO attributeValueDTO);

    AttributeValueDTO toDTO(AttributeValue attributeValue);

    List<AttributeValueDTO> toDTOList(List<AttributeValue> attributeValueList);
}
