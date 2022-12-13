package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.AssistantDto;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.entity.Assistant;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.AssistantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantRepository assistantRepository;

    @PostMapping("/assistants")
    public AssistantDto saveOrUpdateAssistant (@RequestBody AssistantDto dto){
        if(dto.getIdAssistant() == null){
            return AssistantDto.of(assistantRepository.save(Assistant.of(dto)));
        } else {
            Optional<Assistant> optionalAssistant = assistantRepository.findById(dto.getIdAssistant());
            if(optionalAssistant.isPresent()){
                Assistant assistant = optionalAssistant.get();
                assistant.updateAssistant(dto);
                return AssistantDto.of(assistantRepository.save(assistant));
            } else {
                throw new RuntimeException("The user with the given id could not be found: " + dto.getIdAssistant());
                //todo zaimplementuj własny wyjątek i obsługę
            }
        }
    }

    @GetMapping("/assistants")
    public List<AssistantDto> listAssistants(){
        return assistantRepository.findAll()
                .stream()
                .map(AssistantDto::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/assistants/{idAssistant}")
    public AssistantDto getAssistant(@PathVariable Long idAssistant){
        Optional<Assistant> optionalAssistant = assistantRepository.findById(idAssistant);

        return AssistantDto.of(optionalAssistant.get());

        //todo obsłuż wyjątek, gdyby nie było asystenta o podanym id
    }

    @DeleteMapping("/assistants")
    public ResponseEntity deleteAssistant(@RequestBody Long idAssistant){
        assistantRepository.deleteById(idAssistant);
        return ResponseEntity.ok().build();
    }
}
