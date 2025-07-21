package githubrepositoryfetcher.exception;

public class RepositoryNotFoundException extends  RuntimeException{
    public RepositoryNotFoundException(String name){
        super(String.format("Repository %s not found or there is no reposiotry ", name));
    }
}
