package pl.edu.pjatk.gymmanagementapp.exception;

public class CoachWithoutChosenMemberException extends RuntimeException {
    public CoachWithoutChosenMemberException(String errorMessage) {
        super(errorMessage);
    }
}
