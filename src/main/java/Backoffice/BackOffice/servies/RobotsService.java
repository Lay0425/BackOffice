package Backoffice.BackOffice.servies;

import Backoffice.BackOffice.Domain.Repository.RobotsRepository;
import Backoffice.BackOffice.Domain.Robots;
import Backoffice.BackOffice.Domain.SourceType;
import Backoffice.BackOffice.Dto.RegisterRobotRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class RobotsService {

    private final RobotsRepository robotsRepository;
    private final LLMRequestsService llmRequestsService;

    @Autowired
    public RobotsService(RobotsRepository robotsRepository, LLMRequestsService llmRequestsService) {
        this.robotsRepository = robotsRepository;
        this.llmRequestsService = llmRequestsService;
    }

    private String generateSerialNumber(){
        return "SN" + String.format("%08d",(int)(Math.random() * 10000000));
    }

    public Robots registerRobot(RegisterRobotRequest request) {

        Integer typeId = request.getTypeId();

        Robots robot = new Robots();
        robot.setRobotId(request.getRobotId());
        robot.setSerialNumber(generateSerialNumber());
        robot.setType(request.getType());
        robot.setVersion(request.getVersion());

        if(robot.getSerialNumber() == null || robot.getSerialNumber().isEmpty()){
            robot.setSerialNumber(robot.generateSerialNumber());
        }

        Robots savedRobot = robotsRepository.save(robot);

        llmRequestsService.saveLLMRequest(savedRobot.getId(), SourceType.GPT4O_mini, "자동 프롬프트", "자동 응답");

        return savedRobot;
    }

    public Robots updateRobotStatus(String robotId, String type, String version){
        Robots robot = robotsRepository.findByRobotId(robotId);
        if (robot != null) {
            robot.setType(type);
            robot.setVersion(version);
            robotsRepository.save(robot);
            return robotsRepository.save(robot);
        }
        return null;
    }

    public Robots getRobotStatus(String robotId){
        return robotsRepository.findByRobotId(robotId);
    }

    public List<Robots> findAllRobots(){
        return robotsRepository.findAll();
    }

}
