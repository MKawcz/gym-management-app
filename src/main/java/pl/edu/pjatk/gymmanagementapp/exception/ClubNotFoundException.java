package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class ClubNotFoundException extends NoSuchElementException {
    public ClubNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
