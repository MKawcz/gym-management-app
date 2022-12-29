package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.gymmanagementapp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
