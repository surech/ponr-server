package ch.poinzofnoreturn.server.service;

import ch.poinzofnoreturn.server.model.UserClientConnectionEntity;
import ch.poinzofnoreturn.server.model.UserEntity;
import ch.poinzofnoreturn.server.repo.UserClientConnectionRespository;
import ch.poinzofnoreturn.server.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Service für den Zugriff auf die Benutzerdaten
 */
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserClientConnectionRespository userClientConnectionRespository;

    /**
     * Gibt den aktuellen Benutzer zurück
     * @return Aktueller Benutzer. <code>null</code>, wenn niemand angemeldet ist.
     */
    public UserEntity getCurrentUser(){
        // Sind wir überhaupt angemeldet?
        OAuth2Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }

        // Über die Principal erhalten wir die ID des Benutzers
        String userId = principal.toString();

        // ID des OAuth-Provider auslesen
        String clientId = authentication.getOAuth2Request().getClientId();

        // Benutzer holen
        UserEntity user = userRepository.findByOAuthConnection(clientId, userId);

        // Wenn wir keinen Benutzer gefunden haben, dann erstellen wir einen
        if (user == null) {
            user = new UserEntity();
            user = userRepository.save(user);

            UserClientConnectionEntity connectionEntity = new UserClientConnectionEntity();
            connectionEntity.setClientId(clientId);
            connectionEntity.setPrincipalId(userId);
            connectionEntity.setUser(user);
            userClientConnectionRespository.save(connectionEntity);
        }

        // Name abfüllen
        user.setName(getName(authentication));

        return user;
    }

    /**
     * Gibt den Namen des angemeldeten Benutzers zurück
     * @param authentication Details zur OAuth-Authentication
     * @return Name des Benutzers. <code>null</code>, wenn der Name nicht ausgelesen werden konnte
     */
    protected String getName(OAuth2Authentication authentication) {
        // Details auslesen
        if (authentication == null || authentication.getUserAuthentication() == null) {
            return null;
        }

        Object details = authentication.getUserAuthentication().getDetails();
        if (details != null && details instanceof Map) {
            Map detailMap = (Map) details;
            Object name = detailMap.get("name");
            if (name != null) {
                return name.toString();
            }
        }

        // Wenn wir hier sind, könnten wir den Namen nicht auslesen
        return null;
    }

    protected OAuth2Authentication getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Wir wollen eine OAuth-Authentication
        if (authentication != null && authentication instanceof OAuth2Authentication) {
            return (OAuth2Authentication) authentication;
        } else {
            // Entweder haben wir gar keine Authentication oder ein falscher Typ
            return null;
        }
    }
}
