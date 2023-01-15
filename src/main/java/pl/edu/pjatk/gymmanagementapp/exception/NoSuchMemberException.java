package pl.edu.pjatk.gymmanagementapp.exception;

public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException(String errorMessage) {
        super(errorMessage);
    }
}
