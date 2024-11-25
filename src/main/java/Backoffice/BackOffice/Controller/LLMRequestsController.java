package Backoffice.BackOffice.Controller;


import Backoffice.BackOffice.Domain.LLMRequests;
import Backoffice.BackOffice.Domain.Repository.LLMRequestsRepository;
import Backoffice.BackOffice.Domain.SourceType;
import Backoffice.BackOffice.servies.LLMRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/llm-requests")
public class LLMRequestsController {


    private final LLMRequestsService llmRequestsService;
    private final LLMRequestsRepository llmRequestsRepository;

    @Autowired
    public LLMRequestsController(LLMRequestsService llmRequestsService, LLMRequestsRepository llmRequestsRepository) {
        this.llmRequestsService = llmRequestsService;
        this.llmRequestsRepository = llmRequestsRepository;
    }

    @PostMapping("/create")
    public  ResponseEntity<LLMRequests> createLLMRequest(
            @RequestParam(required = false) String robotId,
            @RequestParam(required = false) String serialNumber,
            @RequestParam String source,
            @RequestParam String prompt,
            @RequestParam String response){

        String robotIdString = null;

        if(robotId != null) {
            robotIdString = robotId;
        }else if(serialNumber != null){
            robotIdString = llmRequestsService.findRobotIdBySerialNumber(serialNumber);
            if(robotIdString == null){
                return ResponseEntity.badRequest().body(null);
            }
        } else{
            return ResponseEntity.badRequest().body(null);
        }

        SourceType sourceType;
        try{
            sourceType = SourceType.valueOf(source);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }

        LLMRequests llmRequest = llmRequestsService.saveLLMRequest(robotIdString, sourceType, prompt, response, serialNumber);
        return ResponseEntity.ok(llmRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LLMRequests> getAllLLMRequests(@PathVariable Long id){
        LLMRequests requests = llmRequestsService.findById(id);
        return requests != null ? ResponseEntity.ok(requests) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{serialNumber}")
    public ResponseEntity<LLMRequests> getLLMRequestBySerialNumber(@PathVariable String serialNumber) {
        LLMRequests llmRequest = llmRequestsRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LLM Request not found"));
        return ResponseEntity.ok(llmRequest);
    }

}
