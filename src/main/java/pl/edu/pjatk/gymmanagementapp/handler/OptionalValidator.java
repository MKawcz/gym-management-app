package pl.edu.pjatk.gymmanagementapp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.edu.pjatk.gymmanagementapp.controller.AuthenticationController;
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
public class OptionalValidator {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    public void validateClub(Optional<Club> optionalClub) {
        try {
            optionalClub.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Club which does not exist", e);
            throw new ClubNotFoundException("Club with the given id does not exist");
        }
    }

    public void validateCoach(Optional<Club> optionalClub, Optional<Coach> optionalCoach) {
        try {
            optionalCoach.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Coach which does not exist", e);
            throw new CoachNotFoundException("Coach with the given Id does not exists");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            log.error("Attempt of getting Coach which does not belong to chosen Club");
            throw new CoachNotFoundException("This Club does not have a Coach with the given id");
        }
    }

    public void validateManager(Optional<Club> optionalClub, Optional<Manager> optionalManager) {
        try {
            optionalManager.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Manager which does not exist", e);
            throw new ManagerNotFoundException("Manager with the given Id does not exists");
        }
        if (!optionalClub.get().getManagers().contains(optionalManager.get())) {
            log.warn("Attempt of getting Manager which does not belong to chosen Club");
            throw new ManagerNotFoundException("This Club does not have a Manager with the given id");
        }
    }

    public void validateMember(Optional<Club> optionalClub, Optional<Member> optionalMember) {
        try {
            optionalMember.get();
        } catch (NoSuchElementException e) {
            log.error("Attempt of getting Member which does not exist", e);
            throw new MemberNotFoundException("Member with the given Id does not exists");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            log.error("Attempt of getting Member which does not belong to chosen Club");
            throw new CoachNotFoundException("This Club does not have a Member with the given id");
        }
    }
}
