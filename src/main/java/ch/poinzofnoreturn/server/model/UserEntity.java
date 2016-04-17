package ch.poinzofnoreturn.server.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Enthält die eigentlichen Benutzer
 */
@Entity
@Table(name="user")
public class UserEntity extends CreateTimeTrackEntity {
    /** Name des Benutzers. Dieser kommt nicht aus der Datenbankbank, sondern direkt von OAuth */
    @Transient
    private String name;

    @OneToMany(mappedBy = "user")
    private List<UserClientConnectionEntity> connections;

    /**
     * @return Name des Benutzers. Dieser kommt nicht aus der Datenbankbank, sondern direkt von OAuth
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name des Benutzers. Dieser kommt nicht aus der Datenbankbank, sondern direkt von OAuth
     */
    public void setName(String name) {
        this.name = name;
    }

    public List<UserClientConnectionEntity> getConnections() {
        if (connections == null) {
            connections = new ArrayList<>();
        }
        return connections;
    }
}
