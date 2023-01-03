package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Club;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    public MemberDto saveMember(long clubId, MemberDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        Member member = new Member();
        member.of(dto);
        member.setClub(optionalClub.get());
        optionalClub.get().getMembers().add(member);
        clubRepository.save(optionalClub.get());

        return MemberDto.of(memberRepository.save(member));
    }

    public List<MemberDto> getClubMembers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        return optionalClub.get().getMembers().stream()
                .map(MemberDto::of)
                .toList();
    }

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

    public MemberDto saveClubExistingMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (optionalMember.get().getClub() != null) {
            throw new RuntimeException("This member already has a club");
        }
        if (optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This club already has a member with the given id");
        }

        optionalMember.get().setClub(optionalClub.get());
        optionalClub.get().getMembers().add(optionalMember.get());
        clubRepository.save(optionalClub.get());

        return MemberDto.of(memberRepository.save(optionalMember.get()));
    }

    public List<MemberDto> removeClubMember(long clubId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())){
            throw new RuntimeException("This club does not have a member with the given id");
        }
        if (optionalMember.get().getCoach().getIdCoach() != clubId) {
            throw new RuntimeException("This member does not have a club with the given id");
        }

        optionalClub.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setClub(null);
        optionalMember.get().setCoach(null);
        Club updatedClub = clubRepository.save(optionalClub.get());
        memberRepository.save(optionalMember.get());

        return updatedClub.getMembers().stream().map(MemberDto::of).toList();
    }
}

