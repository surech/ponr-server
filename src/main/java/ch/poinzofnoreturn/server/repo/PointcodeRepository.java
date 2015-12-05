package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.PointcodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Datenbank-Zugriff auf die Codes eines Anbieters
 */
@RepositoryRestResource(collectionResourceRel = "pointcode", path="pointcode")
public interface PointcodeRepository extends CrudRepository<PointcodeEntity, Long> {

}
