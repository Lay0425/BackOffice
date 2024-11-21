package Backoffice.BackOffice.servies;

import Backoffice.BackOffice.Domain.LLMRequests;
import Backoffice.BackOffice.Domain.Repository.LLMRequestsRepository;
import Backoffice.BackOffice.Domain.SourceType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class LLMRequestsService {

    private final LLMRequestsRepository llmRequestsRepository;

    @Autowired
    public LLMRequestsService(LLMRequestsRepository llmRequestsRepository) {
        this.llmRequestsRepository = llmRequestsRepository;
    }

    public LLMRequests saveLLMRequest(Long robotId, SourceType source, String prompt, String response) {
        LLMRequests llmRequest = new LLMRequests();
        llmRequest.setRobotId(robotId);
        llmRequest.setSource(source);
        llmRequest.setPrompt(prompt);
        llmRequest.setResponse(response);
        return llmRequestsRepository.save(llmRequest);
    }

    public LLMRequests finById(Long id){
        return llmRequestsRepository.findById(id).orElse(null);
    }
    public LLMRequests findLLMRequestByRobotId(Long robotId){
        return llmRequestsRepository.findByRobotId(robotId)
                .orElseThrow(() -> new EntityNotFoundException("LLMRequest not fonud for robot" + robotId));

    }
}
