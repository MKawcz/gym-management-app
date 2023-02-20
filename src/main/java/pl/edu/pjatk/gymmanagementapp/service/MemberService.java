package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.exception.*;
import pl.edu.pjatk.gymmanagementapp.validator.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final CoachRepository coachRepository;
    private final OptionalValidator optionalValidator;
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

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

        return MemberDto.of(member);
    }

    @CacheEvict(value = "ClubMembers", allEntries = true)
    public List<MemberDto> assignMemberToClub(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);

        if (optionalMember.isEmpty()) {
            log.error("Attempt of getting Member which does not exist");
            throw new MemberNotFoundException("Member with the given id does not exist");
        }
        if (optionalMember.get().getClub() != null) {
            log.error("Attempt of assigning a Member of id:" + memberId + "who already has a Club, to a Club of id:" + clubId);
            throw new MemberAlreadyHasClubException("This member already has a club");
        }
        if (optionalClub.get().getMembers().contains(optionalMember.get())) {
            log.error("Attempt of assigning a Member of id:" + memberId + " to a Club of id:" + clubId + " which already has a Member with a given id");
            throw new ClubAlreadyHasMemberException("This club already has a member with the given id");
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

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateMember(optionalClub, optionalMember);

        if(optionalMember.get().getCoach() != null) {
            optionalMember.get().setCoach(null);
        }

        optionalClub.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setClub(null);
        Club updatedClub = clubRepository.save(optionalClub.get());
        memberRepository.save(optionalMember.get());

        return updatedClub.getMembers().stream().map(MemberDto::of).toList();
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public List<MemberDto> assignMemberToCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);
        optionalValidator.validateMember(optionalClub, optionalMember);

        if (optionalMember.get().getCoach() != null) {
            log.error("Attempt of assigning Member of id:" + memberId + " who already has a Coach, to a Coach of id:" + coachId);
            throw new MemberAlreadyHasCoachException("This member already has a coach");
        }

        optionalMember.get().setCoach(optionalCoach.get());
        optionalCoach.get().getMembers().add(optionalMember.get());
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public List<MemberDto> removeMemberFromCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);
        optionalValidator.validateMember(optionalClub, optionalMember);

        if (!optionalCoach.get().getMembers().contains(optionalMember.get())){
            log.error("Attempt of assigning Member of id:" + memberId + " to a Coach of id:" + coachId + " which already has a Member with a given id");
            throw new NoSuchMemberAssignedToCoachException("This coach does not have a member with the given id");
        }

        optionalCoach.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setCoach(null);
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }
}

