package githubrepositoryfetcher.response;

import githubrepositoryfetcher.model.Owner;
import lombok.Getter;

@Getter
public record GitHubRepositoryResponse(
        String name,
        boolean fork,
        Owner owner
) {
}
