package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.entity.Address;
import pl.edu.pjatk.gymmanagementapp.entity.Club;

import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.repository.AddressRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ICatalogData;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final AddressRepository addressRepository;

    public ClubDto saveClub(ClubDto dto) {
        Club club = new Club();
        club.of(dto);

        return ClubDto.of(clubRepository.save(club));
    }

    public List<ClubDto> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(ClubDto::of)
                .toList();
    }

    public ClubDto updateClub(long clubId, ClubDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        Club clubToUpdate = optionalClub.get();
        clubToUpdate.of(updatedDto);

        return ClubDto.of(clubRepository.save(clubToUpdate));
    }

    public void deleteClub(long clubId) {
        clubRepository.deleteById(clubId);
    }

    public ClubDto getClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        Club club = optionalClub.get();
        ClubDto dto = ClubDto.of(club);

        return dto;
    }

    public AddressDto getClubAddress(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        return AddressDto.of(optionalClub.get().getAddress());
    }

    public AddressDto saveClubAddress(long clubId, AddressDto dto) {
        var optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalClub.get().getAddress() == null) {
            Address newAddress = new Address();
            Club clubWithNewAddress = configureClubAddress(newAddress, dto, optionalClub.get());

            return AddressDto.of(clubWithNewAddress.getAddress());
        }
        Address addressToUpdate = optionalClub.get().getAddress();
        Club clubWithNewAddress = configureClubAddress(addressToUpdate, dto, optionalClub.get());

        return AddressDto.of(clubWithNewAddress.getAddress());
    }

    private Club configureClubAddress(Address address, AddressDto dto, Club club) {
        address.of(dto);
        address.setClub(club);
        addressRepository.save(address);
        club.setAddress(address);
        Club clubWithNewAddress = clubRepository.save(club);
        return clubWithNewAddress;
    }

}
