package Backoffice.BackOffice.Controller;

import Backoffice.BackOffice.Domain.Robots;
import Backoffice.BackOffice.Dto.RegisterRobotRequest;
import Backoffice.BackOffice.servies.RobotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotsController {

    private final RobotsService robotsService;

    @Autowired
    public RobotsController(RobotsService robotsService) {
        this.robotsService = robotsService;
    }

    /**
     * 로봇 등록
     */
    @PostMapping("/register")
    public ResponseEntity<Robots> registerRobot(@RequestBody RegisterRobotRequest request) {
        Robots robot = robotsService.registerRobot(request);
        return ResponseEntity.status(201).body(robot); // 201 Created 상태 반환
    }

    /**
     * 로봇 상태 업데이트
     */
    @PutMapping("/{robotId}")
    public ResponseEntity<Robots> updateRobotStatus(
            @PathVariable String robotId,
            @RequestBody Robots updatedStatus) {
        Robots robot = robotsService.updateRobotStatus(robotId, updatedStatus.getType(), updatedStatus.getVersion());
        return robot != null ? ResponseEntity.ok(robot) : ResponseEntity.notFound().build();
    }

    /**
     * 특정 로봇 상태 조회
     */
    @GetMapping("/{robotId}")
    public ResponseEntity<Robots> getRobotStatus(@PathVariable String robotId) {
        Robots robot = robotsService.getRobotStatus(robotId);
        return robot != null ? ResponseEntity.ok(robot) : ResponseEntity.notFound().build();
    }

    /**
     * 모든 로봇 조회
     */
    @GetMapping("/all")
    public ResponseEntity<List<Robots>> getAllRobots() {
        List<Robots> robots = robotsService.findAllRobots();
        return ResponseEntity.ok(robots);
    }
}
