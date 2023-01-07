package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @CacheEvict(value = {"ClubMembers", "ClubMember"}, allEntries = true)
    public MemberDto saveMember(long clubId, MemberDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        if (memberRepository.getMemberByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Member with the given email already exists");
        }

        Member member = new Member();
        member.of(dto);
        member.setClub(optionalClub.get());
        optionalClub.get().getMembers().add(member);
        clubRepository.save(optionalClub.get());

        return MemberDto.of(memberRepository.save(member));
    }

    @Cacheable(value = "ClubMembers")
    public List<MemberDto> getClubMembers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        return optionalClub.get().getMembers().stream()
                .map(MemberDto::of)
                .toList();
    }

    @CacheEvict(value = {"ClubMembers", "ClubMember"}, allEntries = true)
    public MemberDto updateClubMember(long clubId, long memberId, MemberDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This Club does not have a member with the given id");
        }

        Member memberToUpdate = optionalMember.get();
        memberToUpdate.of(updatedDto);

        return MemberDto.of(memberRepository.save(memberToUpdate));
    }

    @CacheEvict(value = {"ClubMembers", "ClubMember"}, allEntries = true)
    public void deleteClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This Club does not have a member with the given id");
        }

        memberRepository.delete(optionalMember.get());
    }

    @Cacheable(value = "ClubMember")
    public MemberDto getClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This Club does not have a member with the given id");
        }

        Member member = optionalMember.get();
        MemberDto dto = MemberDto.of(member);

        return dto;

    }

}

