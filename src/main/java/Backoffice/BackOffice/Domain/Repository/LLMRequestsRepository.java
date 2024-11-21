package Backoffice.BackOffice.Domain.Repository;

import Backoffice.BackOffice.Domain.LLMRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LLMRequestsRepository extends JpaRepository<LLMRequests, Long> {
    Optional<LLMRequests> findByRobotId(Long robotId);
}
