package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Address;

@Data
public class AddressDto {

    private String streetName;
    private Integer streetNumber;
    private String postalCode;
    private String city;

    public static AddressDto of(Address address){
        AddressDto dto = new AddressDto();
        dto.setStreetName(address.getStreetName());
        dto.setStreetNumber(address.getStreetNumber());
        dto.setPostalCode(address.getPostalCode());
        dto.setCity(address.getCity());

        return dto;
    }
}
