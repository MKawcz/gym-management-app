package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.OperatorAuthenticationResultDto;
import pl.edu.pjatk.gymmanagementapp.dto.OperatorCredentialsDto;
import pl.edu.pjatk.gymmanagementapp.entity.Operator;
import pl.edu.pjatk.gymmanagementapp.repository.OperatorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;

    @PostMapping("/operators")
    public Operator newOperator (@RequestBody Operator newOperator){
        return operatorRepository.save(newOperator);
    }

    @GetMapping("/operators")
    public List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operators")
    public ResponseEntity deleteOperator(@RequestBody Long idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify_operator_credentials")
    public OperatorAuthenticationResultDto verifyOperatorCredentials(@RequestBody OperatorCredentialsDto operatorCredentialsDto) {
        Optional<Operator> operatorOptional = operatorRepository.findByLogin(operatorCredentialsDto.getLogin());

        if(!operatorOptional.isPresent()){
            return OperatorAuthenticationResultDto.createUnauthenticated();
        }
        Operator operator = operatorOptional.get();
        if(!operator.getPassword().equals(operatorCredentialsDto.getPassword())){
            return OperatorAuthenticationResultDto.createUnauthenticated();
        } else {
            return OperatorAuthenticationResultDto.of(operator);
        }

    }

}
