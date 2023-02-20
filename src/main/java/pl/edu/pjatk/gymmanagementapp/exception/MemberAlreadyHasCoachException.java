package pl.edu.pjatk.gymmanagementapp.exception;

public class MemberAlreadyHasCoachException extends IllegalStateException {
    public MemberAlreadyHasCoachException(String errorMessage) {
        super(errorMessage);
    }
}
