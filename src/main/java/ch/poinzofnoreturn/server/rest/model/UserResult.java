package ch.poinzofnoreturn.server.rest.model;

import ch.poinzofnoreturn.server.model.UserEntity;

/**
 * Enthält Informationen über den angemeldeten Benutzer
 */
public class UserResult {
    private String name;

    public UserResult() {
    }

    public UserResult(UserEntity userEntity) {
        this.name = userEntity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
