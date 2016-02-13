package ch.poinzofnoreturn.server.login;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

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

            // Wenn der Wert nicht mit einem "=" endet, fügen wir es hinzu
            if (!targetUrlEncoded.endsWith("=")) {
                targetUrlEncoded += "=";
            }

            // Inhalt decodieren.
            byte[] bytes = DatatypeConverter.parseBase64Binary(targetUrlEncoded);
            String targetUrl = new String(bytes);

            return targetUrl;
        }

        // Vielleicht hat unsere Basisklasse ja mehr Glück
        return super.determineTargetUrl(request, response);
    }
}
