package ch.poinzofnoreturn.server.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

/**
 * Created by surech on 18.03.16.
 */
@SuppressWarnings("Duplicates")
public class Base64UrlAuthenticationSucessHandlerTest {

    private Base64UrlAuthenticationSucessHandler sut;

    @Before
    public void setUp() throws Exception {
        sut = new Base64UrlAuthenticationSucessHandler();
    }

    @Test
    public void testDetermineTargetUrl() throws Exception {
        // Objekt vorbereiten
        sut.setTargetUrlParameter("redirect");

        // Mocks aufsetzen
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameter("redirect")).thenReturn("aHR0cDovL2xvY2FsaG9zdDozMDAwLyMv");

        // Test ausführen
        String result = sut.determineTargetUrl(request, null);

        // Überprüfen
        Assert.assertEquals("http://localhost:3000/#/", result);
    }

    @Test
    public void testDetermineTargetUrlMitGleich() throws Exception {
        // Objekt vorbereiten
        sut.setTargetUrlParameter("redirect");

        // Mocks aufsetzen
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameter("redirect")).thenReturn("aHR0cDovL2xvY2FsaG9zdDozMDAwLyMv=");

        // Test ausführen
        String result = sut.determineTargetUrl(request, null);

        // Überprüfen
        Assert.assertEquals("http://localhost:3000/#/", result);
    }
}