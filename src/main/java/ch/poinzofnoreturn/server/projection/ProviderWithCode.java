package ch.poinzofnoreturn.server.projection;

import ch.poinzofnoreturn.server.converter.PointDeserializer;
import ch.poinzofnoreturn.server.converter.PointSerializer;
import ch.poinzofnoreturn.server.model.PointcodeEntity;
import ch.poinzofnoreturn.server.model.ProviderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Projektion eines Anbieters inkl. dessen Codes
 */
@Projection(name="providerWithCode", types = {ProviderEntity.class})
public interface ProviderWithCode {
    public String getName();

    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    public Point getLocation();

    public String getStreet();

    public String getZip();

    public String getCity();

    public String getDescription();

    public String getUrl();

    public String getPoinzId();

    public List<PointcodeEntity> getPointcodes();
}
