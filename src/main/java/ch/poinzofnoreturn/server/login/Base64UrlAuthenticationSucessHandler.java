package ch.poinzofnoreturn.server.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

/**
 * Einige OAuth-Provider sind recht heikel, was die übergebenen Parameter angelangt. Facebook z.B. erklaubt keine
 * Sonderzeichen: http://stackoverflow.com/a/5389447
 * Deshalb wird der Parameter Base64-Encoded übergeben. Diesen entschlüsseln wir hier.
 */
public class Base64UrlAuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String targetUrlParameter = getTargetUrlParameter();
        if (targetUrlParameter != null) {
            // Wert auslesen
            String targetUrlEncoded = request.getParameter(targetUrlParameter);

            // Das "=" am Ende immer entfernen
            targetUrlEncoded = StringUtils.stripEnd(targetUrlEncoded, "=");

            // Inhalt decodieren.
            byte[] bytes = Base64.getDecoder().decode(targetUrlEncoded);
            String targetUrl = new String(bytes);

            return targetUrl;
        }

        // Vielleicht hat unsere Basisklasse ja mehr Glück
        return super.determineTargetUrl(request, response);
    }
}
