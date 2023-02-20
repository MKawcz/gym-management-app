package pl.edu.pjatk.gymmanagementapp.exception;

public class NoSuchCoachInClubException extends IllegalArgumentException {
    public NoSuchCoachInClubException(String errorMessage) {
        super(errorMessage);
    }
}
