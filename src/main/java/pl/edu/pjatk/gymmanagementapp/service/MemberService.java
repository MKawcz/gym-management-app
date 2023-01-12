package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.anntotation.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.exception.NoSuchClubException;
import pl.edu.pjatk.gymmanagementapp.exception.NoSuchEntityInChosenClubException;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static pl.edu.pjatk.gymmanagementapp.service.ClubService.validateClub;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @CacheEvict(value = {"ClubMembers", "ClubMember"}, allEntries = true)
    public MemberDto saveMember(long clubId, MemberDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        if (memberRepository.getMemberByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Member with the given email already exists");           //todo custom exc
        }

        Member member = new Member();
        member.of(dto);
        member.setClub(optionalClub.get());
        optionalClub.get().getMembers().add(member);
        clubRepository.save(optionalClub.get());

        return MemberDto.of(memberRepository.save(member));
    }

    @Cacheable(value = "ClubMembers")
    public CachedMembers getClubMembers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        return new CachedMembers (optionalClub.get().getMembers().stream()
                .map(MemberDto::of)
                .toList());
    }

    @CacheEvict(value = {"ClubMembers", "ClubMember"}, allEntries = true)
    public MemberDto updateClubMember(long clubId, long memberId, MemberDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        validateClub(optionalClub);
        validateMember(optionalClub, optionalMember);

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

        validateClub(optionalClub);
        validateMember(optionalClub, optionalMember);

        memberRepository.delete(optionalMember.get());
    }

    @Cacheable(value = "ClubMember", key = "#memberId")
    public MemberDto getClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        validateClub(optionalClub);
        validateMember(optionalClub, optionalMember);

        Member member = optionalMember.get();
        MemberDto dto = MemberDto.of(member);

        return dto;

    }

    protected static void validateMember(Optional<Club> optionalClub, Optional<Member> optionalMember) {
        if (optionalMember.isEmpty() || !optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new NoSuchEntityInChosenClubException("This Club does not have a member with the given id");
        }
    }
}

