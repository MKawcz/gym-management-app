package pl.edu.pjatk.gymmanagementapp.exception;

public class NoSuchManagerInClubException extends IllegalArgumentException {
    public NoSuchManagerInClubException(String errorMessage) {
        super(errorMessage);
    }
}
