package Backoffice.BackOffice.Domain.Repository;

import Backoffice.BackOffice.Domain.Robots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotsRepository extends JpaRepository<Robots, Long> {
    boolean existsBySerialNumber(String serialNumber);

    Robots findByRobotId(String robotId);

    Robots findBySerialNumber(String serialNumber);
}
