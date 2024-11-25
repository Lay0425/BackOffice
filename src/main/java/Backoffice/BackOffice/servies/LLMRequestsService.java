package Backoffice.BackOffice.servies;

import Backoffice.BackOffice.Domain.LLMRequests;
import Backoffice.BackOffice.Domain.Repository.LLMRequestsRepository;
import Backoffice.BackOffice.Domain.Repository.RobotsRepository;
import Backoffice.BackOffice.Domain.Robots;
import Backoffice.BackOffice.Domain.SourceType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LLMRequestsService {

    private final RobotsRepository robotsRepository;
    private final LLMRequestsRepository llmRequestsRepository;

    @Autowired
    public LLMRequestsService(RobotsRepository robotsRepository,LLMRequestsRepository llmRequestsRepository) {
        this.llmRequestsRepository = llmRequestsRepository;
        this.robotsRepository = robotsRepository;
    }

    public LLMRequests saveLLMRequest(String robotId, SourceType source, String prompt, String response, String serialNumber) {
        LLMRequests llmRequest = new LLMRequests();
        llmRequest.setRobotId(robotId);
        llmRequest.setSource(source);
        llmRequest.setPrompt(prompt);
        llmRequest.setResponse(response);

        if(serialNumber == null || serialNumber.isEmpty()){
            throw new IllegalArgumentException("serialNumber cannot be full or empty");
        }

        llmRequest.setSerialNumber(serialNumber);
        return llmRequestsRepository.save(llmRequest);
    }

    public LLMRequests findById(Long id){
        return llmRequestsRepository.findById(id).orElse(null);
    }

    public String findRobotIdBySerialNumber(String serialNumber) {
        Robots robot = robotsRepository.findBySerialNumber(serialNumber);
        if (robot == null) {
            throw new EntityNotFoundException("Robot with serial number " + serialNumber + " not found.");
        }
        return robot.getRobotId();
    }
}
