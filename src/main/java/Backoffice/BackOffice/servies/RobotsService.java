package Backoffice.BackOffice.servies;

import Backoffice.BackOffice.Domain.Repository.RobotsRepository;
import Backoffice.BackOffice.Domain.Robots;
import Backoffice.BackOffice.Domain.SourceType;
import Backoffice.BackOffice.Dto.RegisterRobotRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    RobotsService {

    private final RobotsRepository robotsRepository;
    private final LLMRequestsService llmRequestsService;

    @Autowired
    public RobotsService(RobotsRepository robotsRepository, LLMRequestsService llmRequestsService) {
        this.robotsRepository = robotsRepository;
        this.llmRequestsService = llmRequestsService;
    }

    private String generateSerialNumber(){
        String serialNumber;
        do {
            serialNumber = "SN" + String.format("%08d", (int) (Math.random() * 10000000));
        } while (robotsRepository.existsBySerialNumber(serialNumber));
        return serialNumber;
    }

    public Robots registerRobot(RegisterRobotRequest request) {

        Integer typeId = request.getTypeId();

        Robots robot = new Robots();
        robot.setRobotId(request.getRobotId());
        robot.setSerialNumber(generateSerialNumber());
        robot.setType(request.getType());
        robot.setVersion(request.getVersion());

        Robots savedRobot = robotsRepository.save(robot);

        System.out.println("Generated Serial Number: " + savedRobot.getSerialNumber());


        llmRequestsService.saveLLMRequest(String.valueOf(savedRobot.getId()), SourceType.GPT4O_mini, "자동 프롬프트", "자동 응답", savedRobot.getSerialNumber());

        return savedRobot;
    }

    public Robots updateRobotStatus(String robotId, String type, String version){
        Robots robot = robotsRepository.findByRobotId(robotId);
        if (robot == null) {
            throw new IllegalArgumentException("Robot with ID" + robotId + "not found");
        }
        robot.setType(type);
        robot.setVersion(version);
        return robotsRepository.save(robot);
    }

    public Optional<Robots> getRobotStatus(String robotId){
        return Optional.ofNullable(robotsRepository.findByRobotId(robotId));
    }

    public List<Robots> findAllRobots(){
        return robotsRepository.findAll();
    }

    public Robots getRobotBySerialNumber(String serialNumber){
        return robotsRepository.findBySerialNumber(serialNumber);
    }
}
