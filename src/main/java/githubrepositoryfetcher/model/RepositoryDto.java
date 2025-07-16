package githubrepositoryfetcher.model;

import java.util.List;

public record RepositoryDto(
        String name,
        String ownerLogin,
        List<BranchDto> branches
) {
}
