package pl.edu.pjatk.gymmanagementapp.exception;

public class ClubAlreadyWithMemberException extends RuntimeException {
    public ClubAlreadyWithMemberException(String errorMessage) {
        super(errorMessage);
    }
}
