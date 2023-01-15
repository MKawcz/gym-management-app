package pl.edu.pjatk.gymmanagementapp.model.enums;

import lombok.Getter;

import java.time.Duration;

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