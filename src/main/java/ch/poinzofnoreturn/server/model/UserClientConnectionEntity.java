package ch.poinzofnoreturn.server.model;

import javax.persistence.*;

/**
 * Enthält die Verbindung eines Benutzers mit einem oder mehreren OAuth-Logins
 */
@Entity
@Table(name="userclientconnection")
public class UserClientConnectionEntity extends CreateTimeTrackEntity {

    @ManyToOne
    @JoinColumn(name="user_fk", nullable = false)
    private UserEntity user;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "PRINCIPAL_ID", nullable = false)
    private String principalId;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
}
