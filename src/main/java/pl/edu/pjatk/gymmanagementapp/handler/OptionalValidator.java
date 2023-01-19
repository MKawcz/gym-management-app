package pl.edu.pjatk.gymmanagementapp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.pjatk.gymmanagementapp.exception.ClubNotFoundException;
import pl.edu.pjatk.gymmanagementapp.exception.CoachNotFoundException;
import pl.edu.pjatk.gymmanagementapp.exception.ManagerNotFoundException;
import pl.edu.pjatk.gymmanagementapp.exception.MemberNotFoundException;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.model.Manager;
import pl.edu.pjatk.gymmanagementapp.model.Member;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Slf4j
public class OptionalValidator {

    public void validateClub(Optional<Club> optionalClub) {
        try {
            optionalClub.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Club which does not exist", e);
            throw new ClubNotFoundException("Club with the given id does not exist");
        }
    }

    public void validateCoach(Optional<Club> optionalClub, Optional<Coach> optionalCoach) {
        if (optionalCoach.isEmpty() || !optionalClub.get().getCoaches().contains(optionalCoach)) {
            log.error("Attempt of getting Coach which does not belong to chosen Club");
            throw new CoachNotFoundException("This Club does not have a coach with the given id");
        }
    }

    public void validateManager(Optional<Club> optionalClub, Optional<Manager> optionalManager) {
        try {
            optionalManager.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Manager which does not exist", e);
            throw new ManagerNotFoundException("Manager with the given Id does not exists");            //todo zamień wyjatęk żeby rozszerzał nosuchelement
        }

        if (!optionalClub.get().getManagers().contains(optionalManager.get())) {
            log.warn("Attempt of getting Manager which does not belong to chosen Club");
            throw new ManagerNotFoundException("This Club does not have a manager with the given id");
        }
    }

    public void validateMember(Optional<Club> optionalClub, Optional<Member> optionalMember) {
        if (optionalMember.isEmpty() || !optionalClub.get().getMembers().contains(optionalMember.get())) {
            log.error("Attempt of getting Member which does not belong to chosen Club");
            throw new MemberNotFoundException("This Club does not have a member with the given id");
        }
    }
}
