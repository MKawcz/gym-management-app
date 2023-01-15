package pl.edu.pjatk.gymmanagementapp.exception;

import java.util.NoSuchElementException;

public class MemberNotFoundException extends NoSuchElementException {
    public MemberNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
