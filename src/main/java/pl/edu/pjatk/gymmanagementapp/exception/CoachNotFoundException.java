package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class CoachNotFoundException extends NoSuchElementException {
    public CoachNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
