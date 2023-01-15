package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.model.Address;

@Data
public class AddressDto {

    @NotBlank(message = "Field streetName cannot be blank and cannot be null")
    @Size(max=50, message = "Field streetName should be max 50 characters long")
    private String streetName;

    @NotNull(message = "Field streetNumber cannot be null")
    private Integer streetNumber;

    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Provided zip code has an invalid format (00-000)")
    private String zipCode;

    @NotBlank(message = "Field city cannot be blank and cannot be null")
    @Size(max=50, message = "Field city should be max 50 characters long")
    private String city;

    public static AddressDto of(Address address){
        AddressDto dto = new AddressDto();
        dto.setStreetName(address.getStreetName());
        dto.setStreetNumber(address.getStreetNumber());
        dto.setZipCode(address.getZipCode());
        dto.setCity(address.getCity());

        return dto;
    }
}
