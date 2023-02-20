package pl.edu.pjatk.gymmanagementapp.exception;

public class MemberAlreadyHasClubException extends IllegalStateException {
    public MemberAlreadyHasClubException(String errorMessage) {
        super(errorMessage);
    }
}
