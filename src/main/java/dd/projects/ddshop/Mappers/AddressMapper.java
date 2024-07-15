package dd.projects.ddshop.Mappers;

import dd.projects.ddshop.DTOs.AddressDTO;
import dd.projects.ddshop.Entities.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDTO(Address address);

    List<AddressDTO> toDTOList(List<Address> addressList);
}
