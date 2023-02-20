package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class AddressNotFoundException extends NoSuchElementException {
    public AddressNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
