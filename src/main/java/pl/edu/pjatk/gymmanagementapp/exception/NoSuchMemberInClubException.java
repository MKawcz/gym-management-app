package pl.edu.pjatk.gymmanagementapp.exception;

public class NoSuchMemberInClubException extends IllegalArgumentException {
    public NoSuchMemberInClubException(String errorMessage) {
        super(errorMessage);
    }
}
