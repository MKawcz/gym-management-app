package pl.edu.pjatk.gymmanagementapp.exception;

public class MemberAlreadyWithCoachException extends RuntimeException {
    public MemberAlreadyWithCoachException(String errorMessage) {
        super(errorMessage);
    }
}
