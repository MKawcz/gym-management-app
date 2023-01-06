package pl.edu.pjatk.gymmanagementapp.entity.enums;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

//public class Membership {
//
//    private LocalDateTime startDate;
//    private MembershipType membershipType;
//    private LocalDateTime endDate;
//
//    public Membership(LocalDateTime startDate, MembershipType membershipType) {
//        this.startDate = startDate;
//        this.membershipType = membershipType;
//        this.endDate = startDate.plus(membershipType.getDuration());
//    }
//
//    public LocalDateTime getStartDate() {
//        return startDate;
//    }
//
//    public LocalDateTime getEndDate() {
//        return endDate;
//    }
//}

@Getter
public enum MembershipType {
    DAILY(Duration.ofDays(1)),
    MONTHLY(Duration.ofDays(30)),
    SIX_MONTH(Duration.ofDays(180)),
    ANNUAL(Duration.ofDays(365));

    private final Duration duration;

    MembershipType(Duration duration) {
        this.duration = duration;
    }

    //todo start & end date

}