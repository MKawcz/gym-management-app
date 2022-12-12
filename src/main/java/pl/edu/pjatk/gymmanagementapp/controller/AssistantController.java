package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Assistant;
import pl.edu.pjatk.gymmanagementapp.repository.AssistantRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantRepository assistantRepository;

    @PostMapping("/assistants")
    public Assistant newAssistant (@RequestBody Assistant newAssistant){
        return assistantRepository.save(newAssistant);
    }

    @GetMapping("/assistants")
    public List<Assistant> listAssistants(){
        return assistantRepository.findAll();
    }

    @DeleteMapping("/assistants")
    public ResponseEntity deleteAssistant(@RequestBody Long idAssistant){
        assistantRepository.deleteById(idAssistant);
        return ResponseEntity.ok().build();
    }
}
