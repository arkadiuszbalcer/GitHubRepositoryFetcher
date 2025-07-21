package githubrepositoryfetcher.exception;

public class InternalServerException extends RuntimeException{
    public InternalServerException(){
        super("Internal  server exception");
    }
}
