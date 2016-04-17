package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Datenbank-Zugriff auf die Benutzerdaten
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN u.connections c WHERE c.clientId = :clientId AND c.principalId = :principalId")
    UserEntity findByOAuthConnection(@Param("clientId") String clientId, @Param("principalId") String userId);
}
