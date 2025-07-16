package githubrepositoryfetcher.model;

public record BranchDto(
        String name,
        String lastCommitSha
) {
}
