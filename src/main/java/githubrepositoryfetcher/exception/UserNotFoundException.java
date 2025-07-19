package githubrepositoryfetcher.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String ownerLogin){
        super(String.format("User with login name %s not found", ownerLogin));
    }
}
