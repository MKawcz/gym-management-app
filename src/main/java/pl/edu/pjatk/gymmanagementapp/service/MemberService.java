package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.controller.ClubController;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.exception.MemberNotFoundException;
import pl.edu.pjatk.gymmanagementapp.handler.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final OptionalValidator optionalValidator;

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public MemberDto saveMember(long clubId, MemberDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

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

        optionalValidator.validateClub(optionalClub);

        return new CachedMembers (optionalClub.get().getMembers().stream()
                .map(MemberDto::of)
                .toList());
    }

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public MemberDto updateClubMember(long clubId, long memberId, MemberDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateMember(optionalClub, optionalMember);

        Member memberToUpdate = optionalMember.get();
        memberToUpdate.of(updatedDto);

        return MemberDto.of(memberRepository.save(memberToUpdate));
    }

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public void deleteClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateMember(optionalClub, optionalMember);

        memberRepository.delete(optionalMember.get());
    }

    public MemberDto getClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateMember(optionalClub, optionalMember);

        Member member = optionalMember.get();
        MemberDto dto = MemberDto.of(member);

        return dto;
    }
}

