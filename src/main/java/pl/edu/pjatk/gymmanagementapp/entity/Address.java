package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.AddressDto;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetName;
    private Integer streetNumber;
    private String postalCode;
    private String city;
    @OneToOne(mappedBy = "address")
    private Club club;

    public void of(AddressDto dto) {
        if(dto.getStreetName() != null) {
            this.setStreetName(dto.getStreetName());
        }
        if(dto.getStreetNumber() != null) {
            this.setStreetNumber(dto.getStreetNumber());
        }
        if(dto.getPostalCode() != null) {
            this.setPostalCode(dto.getPostalCode());
        }
        if(dto.getCity() != null) {
            this.setCity(dto.getCity());
        }
    }
}
