package githubrepositoryfetcher.model;

public class RepositoryNotFoundException extends  RuntimeException{
    public RepositoryNotFoundException(String name){
        super(String.format("Repository %s not found", name));
    }
}
