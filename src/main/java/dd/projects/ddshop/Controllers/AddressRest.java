package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.DTOs.AddressDTO;
import dd.projects.ddshop.Services.AddressService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/address")
public class AddressRest {

    AddressService addressService;

    public AddressRest(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addAddress(@RequestBody AddressDTO addressDTO) {
        try {
            return addressService.addAddress(addressDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        try {
            return addressService.getAllAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Integer id) {
        try {
            return addressService.getAddressById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AddressDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getByUser/{id}")
    public ResponseEntity<List<AddressDTO>> getAddressByUser(@PathVariable Integer id) {
        try {
            return addressService.getAddressByUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
