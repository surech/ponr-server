package ch.poinzofnoreturn.server.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Point;

import java.io.IOException;

/**
 * Wandelt einen Punkt in JSON um
 */
public class PointSerializer extends JsonSerializer<Point> {
    public static final String LONGITUDE_KEY = "longitude";
    public static final String LATITUDE_KEY = "latitude";

    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(LONGITUDE_KEY, point.getX());
        jsonGenerator.writeNumberField(LATITUDE_KEY, point.getY());
        jsonGenerator.writeEndObject();
    }
}
