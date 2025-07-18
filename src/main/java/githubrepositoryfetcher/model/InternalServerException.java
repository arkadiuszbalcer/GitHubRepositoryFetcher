package githubrepositoryfetcher.model;

public class InternalServerException extends RuntimeException{
    public InternalServerException(){
        super("Internal  server exception");
    }
}
