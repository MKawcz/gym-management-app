package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Employee {

    private String firstName;

    private String lastName;

    private Integer salary;

}
