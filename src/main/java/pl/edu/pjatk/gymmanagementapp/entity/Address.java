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
    private String zipCode;
    private String city;
    @OneToOne(mappedBy = "address")
    private Club club;

    public void of(AddressDto dto) {
        this.setStreetName(dto.getStreetName());
        this.setStreetNumber(dto.getStreetNumber());
        this.setZipCode(dto.getZipCode());
        this.setCity(dto.getCity());
    }
}
