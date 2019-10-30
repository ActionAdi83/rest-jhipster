package ro.siveco.hipster.repository;
import ro.siveco.hipster.domain.AppLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppLogsRepository extends JpaRepository<AppLogs, Long> {

}
