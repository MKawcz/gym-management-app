package pl.edu.pjatk.gymmanagementapp.exception;

public class MemberAlreadyWithClubException extends RuntimeException {
    public MemberAlreadyWithClubException(String errorMessage) {
        super(errorMessage);
    }
}
