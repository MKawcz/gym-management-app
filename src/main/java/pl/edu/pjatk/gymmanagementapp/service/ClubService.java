package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedClubs;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.exception.*;
import pl.edu.pjatk.gymmanagementapp.validator.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Address;
import pl.edu.pjatk.gymmanagementapp.model.Club;

import pl.edu.pjatk.gymmanagementapp.repository.AddressRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final AddressRepository addressRepository;
    private final OptionalValidator optionalValidator;

    private static final Logger log = LoggerFactory.getLogger(ClubService.class);

    @CacheEvict(value = "AllClubs", allEntries = true)
    public ClubDto saveClub(ClubDto dto) {
        Club club = new Club();
        club.of(dto);

        return ClubDto.of(clubRepository.save(club));
    }

    @Cacheable(value = "AllClubs")
    public CachedClubs getAllClubs() {
        return new CachedClubs(clubRepository.findAll().stream()
                .map(ClubDto::of)
                .toList());
    }

    @CacheEvict(value = "AllClubs", allEntries = true)
    public ClubDto updateClub(long clubId, ClubDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        Club clubToUpdate = optionalClub.get();
        clubToUpdate.of(updatedDto);

        return ClubDto.of(clubRepository.save(clubToUpdate));
    }

    @CacheEvict(value = "AllClubs", allEntries = true)
    public void deleteClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        clubRepository.delete(optionalClub.get());
    }

    public ClubDto getClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        Club club = optionalClub.get();

        return ClubDto.of(club);
    }

    public AddressDto getClubAddress(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        if (optionalClub.get().getAddress() == null) {
            log.error("Attempt of getting an Address of a Club of id:" + clubId + " which does not have one");
            throw new AddressNotFoundException("Club with the given id does not have an address");
        }

        return AddressDto.of(optionalClub.get().getAddress());
    }

    public AddressDto saveClubAddress(long clubId, AddressDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

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

        return clubRepository.save(club);
    }
}
