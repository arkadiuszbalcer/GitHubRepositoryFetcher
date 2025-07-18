package githubrepositoryfetcher.model;

import lombok.Builder;

@Builder
public record BranchDto(
        String name,
        String lastCommitSha
) {
}
