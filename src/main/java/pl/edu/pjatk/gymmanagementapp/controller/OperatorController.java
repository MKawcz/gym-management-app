package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Operator;
import pl.edu.pjatk.gymmanagementapp.repository.OperatorRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;

    @PostMapping("/operators")
    Operator newOperator (@RequestBody Operator newOperator){
        return operatorRepository.save(newOperator);
    }

    @GetMapping("/operators")
    List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operators")
    ResponseEntity deleteOperator(@RequestBody Long idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }
}
