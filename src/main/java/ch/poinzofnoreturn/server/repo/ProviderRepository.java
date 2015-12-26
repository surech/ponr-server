package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.ProviderEntity;
import ch.poinzofnoreturn.server.projection.ProviderWithCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Datenbank-Zugriff auf die Anbieter
 */
@RepositoryRestResource(collectionResourceRel = "provider", path="provider", excerptProjection = ProviderWithCode.class)
public interface ProviderRepository extends CrudRepository<ProviderEntity, Long> {

}
