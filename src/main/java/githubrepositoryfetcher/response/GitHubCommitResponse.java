package githubrepositoryfetcher.response;

import githubrepositoryfetcher.model.Commit;
import lombok.Builder;

@Builder
public record GitHubCommitResponse(Commit commit) {
}
