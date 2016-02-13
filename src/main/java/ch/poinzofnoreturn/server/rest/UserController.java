package ch.poinzofnoreturn.server.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controller für den Zugriff auf den (angemeldeten) Benutzer
 */
@RestController
public class UserController {
    @RequestMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("Benutzer ist angemeldet: " +principal.getName());
        return principal;
    }
}
