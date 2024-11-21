package Backoffice.BackOffice.Controller;


import Backoffice.BackOffice.Domain.LLMRequests;
import Backoffice.BackOffice.Domain.Repository.LLMRequestsRepository;
import Backoffice.BackOffice.Domain.SourceType;
import Backoffice.BackOffice.servies.LLMRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/llm-requests")
public class LLMRequestsController {


    private LLMRequestsService llmRequestsService;

    @Autowired
    public LLMRequestsController(LLMRequestsService llmRequestsService) {
        this.llmRequestsService = llmRequestsService;
    }
    @PostMapping("/create")
    public  ResponseEntity<LLMRequests> createLLMRequest(
            @RequestParam String robotId,
            @RequestParam String source,
            @RequestParam String prompt,
            @RequestParam String response){

        Long robotIdLong;
        try{
            robotIdLong = Long.parseLong(robotId);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }

        SourceType sourceType;
        try{
            sourceType = SourceType.valueOf(source);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }

        LLMRequests llmRequest = llmRequestsService.saveLLMRequest(robotIdLong, sourceType, prompt, response );
        return ResponseEntity.ok(llmRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LLMRequests> getAllLLMRequests(@PathVariable Long id){
        LLMRequests requests = llmRequestsService.finById(id);
        return requests != null ? ResponseEntity.ok(requests) : ResponseEntity.notFound().build();
    }

}
