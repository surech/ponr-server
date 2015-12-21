package ch.poinzofnoreturn.server.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import java.io.IOException;

/**
 * Extrahiert einen Punkt aus JSON
 */
public class PointDeserializer extends JsonDeserializer<Point> {

    public static final String LONGITUDE_KEY = "longitude";
    public static final String LATITUDE_KEY = "latitude";
    private final static GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        double longitude = 0;
        double latitude = 0;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            // Name des Feldes auslesen
            String name = jsonParser.getCurrentName();
            if (LONGITUDE_KEY.equalsIgnoreCase(name)) {
                // Nächstes Token einlesen, welche den Wert enthält
                jsonParser.nextToken();
                longitude = jsonParser.getDoubleValue();
            } else if (LATITUDE_KEY.equalsIgnoreCase(name)) {
                // Nächstes Token einlesen, welche den Wert enhält
                jsonParser.nextToken();
                latitude = jsonParser.getDoubleValue();
            }
        }

        // Punkt erstellen
        Point result = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        return result;
    }
}
