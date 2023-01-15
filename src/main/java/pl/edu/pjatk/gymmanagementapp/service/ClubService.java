package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedClubs;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.exception.*;
import pl.edu.pjatk.gymmanagementapp.model.Address;
import pl.edu.pjatk.gymmanagementapp.model.Club;

import pl.edu.pjatk.gymmanagementapp.repository.AddressRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static pl.edu.pjatk.gymmanagementapp.service.MemberService.validateMember;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @CacheEvict(value = {"AllClubs", "Club"}, allEntries = true)
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

    @CacheEvict(value = {"AllClubs", "Club"}, allEntries = true)
    public ClubDto updateClub(long clubId, ClubDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);

       validateClub(optionalClub);

        Club clubToUpdate = optionalClub.get();
        clubToUpdate.of(updatedDto);

        return ClubDto.of(clubRepository.save(clubToUpdate));
    }

    @CacheEvict(value = {"AllClubs", "Club"}, allEntries = true)
    public void deleteClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        clubRepository.delete(optionalClub.get());
    }

    @Cacheable(value = "Club")
    public ClubDto getClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        Club club = optionalClub.get();
        ClubDto dto = ClubDto.of(club);

        return dto;
    }

    @Cacheable(value = "ClubAddress")
    public AddressDto getClubAddress(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        if (optionalClub.get().getAddress() == null) {
            throw new ClubWithoutAddressException("Club with the given id does not have an address");
        }

        return AddressDto.of(optionalClub.get().getAddress());
    }

    @CacheEvict(value = "ClubAddress", allEntries = true)
    public AddressDto saveClubAddress(long clubId, AddressDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

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

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public List<MemberDto> assignMemberToClub(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        validateClub(optionalClub);

        if (optionalMember.isEmpty()) {
            throw new NoSuchMemberException("Member with the given id does not exist");
        }
        if (optionalMember.get().getClub() != null) {
            throw new MemberAlreadyWithClubException("This member already has a club");
        }
        if (optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new ClubAlreadyWithMemberException("This club already has a member with the given id");
        }

        optionalMember.get().setClub(optionalClub.get());
        optionalClub.get().getMembers().add(optionalMember.get());
        Club updatedClub = clubRepository.save(optionalClub.get());
        memberRepository.save(optionalMember.get());

        return updatedClub.getMembers().stream().map(MemberDto::of).toList();
    }

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public List<MemberDto> removeMemberFromClub(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        validateClub(optionalClub);
        validateMember(optionalClub, optionalMember);

        if(optionalMember.get().getCoach() != null) {
            optionalMember.get().setCoach(null);
        }

        optionalClub.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setClub(null);
        Club updatedClub = clubRepository.save(optionalClub.get());
        memberRepository.save(optionalMember.get());

        return updatedClub.getMembers().stream().map(MemberDto::of).toList();
    }

    public static void validateClub(Optional<Club> optionalClub) {
        try {
            optionalClub.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Club which does not exist");
            throw new ClubNotFoundException("Club with the given id does not exist");
        }
    }

}
