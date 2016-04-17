package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.UserClientConnectionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Datenbankzugriff auf die Verbindung zwischen Benutzer und OAuth-Login
 */
public interface UserClientConnectionRespository extends CrudRepository<UserClientConnectionEntity, Long> {
}
