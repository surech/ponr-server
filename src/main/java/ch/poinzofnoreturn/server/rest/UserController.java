package ch.poinzofnoreturn.server.rest;

import ch.poinzofnoreturn.server.model.UserEntity;
import ch.poinzofnoreturn.server.rest.model.UserResult;
import ch.poinzofnoreturn.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controller für den Zugriff auf den (angemeldeten) Benutzer
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public UserResult user(Principal principal) {
        UserEntity currentUser = userService.getCurrentUser();
        if(currentUser != null){
            UserResult result = new UserResult(currentUser);
            return result;
        } else {
            return null;
        }
    }
}
