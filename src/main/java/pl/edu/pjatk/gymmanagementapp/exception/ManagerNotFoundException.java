package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class ManagerNotFoundException extends NoSuchElementException {
    public ManagerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
