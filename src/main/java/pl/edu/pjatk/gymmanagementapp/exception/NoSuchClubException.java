package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class NoSuchClubException extends NoSuchElementException {
    public NoSuchClubException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
