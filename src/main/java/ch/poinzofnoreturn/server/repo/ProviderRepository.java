package ch.poinzofnoreturn.server.repo;

import ch.poinzofnoreturn.server.model.ProviderEntity;
import ch.poinzofnoreturn.server.projection.ProviderWithCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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
    Page<ProviderEntity> findProviderWithPointcodes(Pageable p);

    @Query("SELECT p FROM ProviderEntity p WHERE p.name like %:searchTerm% or p.city like %:searchTerm%")
    @RestResource(path = "fulltext", rel = "fulltext")
    Page<ProviderEntity> findProviderByFulltext(Pageable p, @Param("searchTerm") String text);

    @Query("SELECT p FROM ProviderEntity p JOIN p.pointcodes c WHERE p.name like %:searchTerm% or p.city like %:searchTerm%")
    @RestResource(path = "fulltextWithCodes", rel = "fulltextWithCodes")
    Page<ProviderEntity> findProviderByFulltextWithCodes(Pageable p, @Param("searchTerm") String text);
}
