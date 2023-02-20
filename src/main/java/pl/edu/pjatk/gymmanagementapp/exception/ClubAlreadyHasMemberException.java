package pl.edu.pjatk.gymmanagementapp.exception;

public class ClubAlreadyHasMemberException extends IllegalStateException {
    public ClubAlreadyHasMemberException(String errorMessage) {
        super(errorMessage);
    }
}
