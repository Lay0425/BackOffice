package Backoffice.BackOffice.Domain.Repository;
import Backoffice.BackOffice.Domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long>{
    List<Contents> findByType(int type);
}
