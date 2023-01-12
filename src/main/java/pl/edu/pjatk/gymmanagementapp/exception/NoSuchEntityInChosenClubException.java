package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class NoSuchEntityInChosenClubException extends RuntimeException {
    public NoSuchEntityInChosenClubException(String errorMessage) {
        super(errorMessage);
    }
}
