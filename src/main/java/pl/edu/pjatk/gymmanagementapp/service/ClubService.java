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
    private final MemberRepository memberRepository;

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
        if(optionalClub.isPresent()) {
            Club clubToUpdate = optionalClub.get();
            clubToUpdate.of(updatedDto);
            return ClubDto.of(clubRepository.save(clubToUpdate));
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public void deleteClub(long clubId) {
        clubRepository.deleteById(clubId);
    }

    public ClubDto getClub(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            Club club = optionalClub.get();
            ClubDto dto = ClubDto.of(club);

            return dto;
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<MemberDto> getClubMembers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getMembers().stream()
                    .map(MemberDto::of).
                    toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<CoachDto> getClubCoaches(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getCoaches().stream()
                    .map(CoachDto::of)
                    .toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<ManagerDto> getClubManagers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getManagers().stream()
                    .map(ManagerDto::of)
                    .toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public AddressDto getClubAddress(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            return AddressDto.of(optionalClub.get().getAddress());
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

    public AddressDto saveClubAddress(long clubId, AddressDto dto) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            if(optionalClub.get().getAddress() == null) {
                Address newAddress = new Address();
                Club clubWithNewAddress = configureClubAddress(newAddress, dto, optionalClub.get());
                return AddressDto.of(clubWithNewAddress.getAddress());
            }
            Address addressToUpdate = optionalClub.get().getAddress();
            Club clubWithNewAddress = configureClubAddress(addressToUpdate, dto, optionalClub.get());
            return AddressDto.of(clubWithNewAddress.getAddress());
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

    private Club configureClubAddress(Address address, AddressDto dto, Club club) {
        address.of(dto);
        address.setClub(club);
        addressRepository.save(address);
        club.setAddress(address);
        Club clubWithNewAddress = clubRepository.save(club);
        return clubWithNewAddress;
    }

    //todo add & remove: member, manager, coach
    //todo set member, manager, coach club
    //managerow i coachow mozna tylko tworzyc i usuwac po dodaniu do klubu
    //do klubu mozna przypisac istniejacego lub nowego membera (tak samo jak w coach)
    //oraz mozna go tworzyc i usuwac od strony samego member service

    public MemberDto saveClubNewMember(long clubId, MemberDto dto) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            Member member = new Member();
            member.of(dto);
            member.setClub(optionalClub.get());
            return MemberDto.of(memberRepository.save(member));
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<MemberDto> removeClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);
        if(optionalClub.isPresent()) {
            if (optionalMember.isPresent()) {
                if (optionalClub.get().getMembers().contains(optionalMember.get())) {
                    optionalClub.get().getMembers().remove(optionalMember.get());
                    optionalMember.get().setClub(null);
                    memberRepository.save(optionalMember.get());
                    Club updatedClub = clubRepository.save(optionalClub.get());
                    return updatedClub.getMembers().stream().map(MemberDto::of).toList();
                }
                throw new RuntimeException("This Club does not have a member with the given id");
            }
            throw new RuntimeException("Member with the given id does not exist");
        }
        throw new RuntimeException("Club with the given id does not exist");
    }
}
