package githubrepositoryfetcher.response;

import githubrepositoryfetcher.model.Owner;

public record GitHubRepositoryResponse(
        String name,
        boolean fork,
        Owner owner
) {
}
