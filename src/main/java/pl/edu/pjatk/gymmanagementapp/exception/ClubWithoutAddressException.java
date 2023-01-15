package pl.edu.pjatk.gymmanagementapp.exception;

public class ClubWithoutAddressException extends RuntimeException{
    public ClubWithoutAddressException(String errorMessage) {
        super(errorMessage);
    }
}
