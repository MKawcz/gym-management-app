package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Operator;

@Data
public class OperatorAuthenticationResultDto {

    private Long idOperator;
    private String firstName;
    private String lastName;
    private boolean authentication;

    public static OperatorAuthenticationResultDto createUnauthenticated(){
        OperatorAuthenticationResultDto dto = new OperatorAuthenticationResultDto();
        dto.setAuthentication(false);
        return dto;
    }

    public static OperatorAuthenticationResultDto of(Operator operator){
        OperatorAuthenticationResultDto dto = new OperatorAuthenticationResultDto();
        dto.setAuthentication(true);
        dto.setFirstName(operator.getManager().getEmployee().getFirstName());
        dto.setLastName(operator.getManager().getEmployee().getLastName());
        dto.setIdOperator(operator.getIdOperator());

        return dto;
    }

}
