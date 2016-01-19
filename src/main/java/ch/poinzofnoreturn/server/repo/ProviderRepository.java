package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.ProviderEntity;
import ch.poinzofnoreturn.server.projection.ProviderWithCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Datenbank-Zugriff auf die Anbieter
 */
@RepositoryRestResource(collectionResourceRel = "provider", path="provider", excerptProjection = ProviderWithCode.class)
public interface ProviderRepository extends PagingAndSortingRepository<ProviderEntity, Long> {

    @Query("SELECT p FROM ProviderEntity p JOIN p.pointcodes c")
    @RestResource(path = "onlyWithCodes", rel = "onlyWithCodes")
    public Page<ProviderEntity> findProviderWithPointcodes(Pageable p);
}
