package pl.edu.pjatk.gymmanagementapp.exception;

public class UserAlreadyExistsException extends IllegalStateException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
