package pl.edu.pjatk.gymmanagementapp.exception;

public class NoSuchMemberAssignedToCoachException extends IllegalStateException {
    public NoSuchMemberAssignedToCoachException(String errorMessage) {
        super(errorMessage);
    }
}
