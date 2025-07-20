package githubrepositoryfetcher.controller;

import githubrepositoryfetcher.model.BranchDto;
import githubrepositoryfetcher.model.RepositoryDto;
import githubrepositoryfetcher.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class GitHubController {

private final GithubService githubService;

@GetMapping("/repos/{userName}/{repositoryName}")

    public List<RepositoryDto> getUsersRepos(
        @PathVariable String userName,
        @PathVariable String repositoryName){

    List<RepositoryDto> repository = githubService.fetchRepositories(userName, repositoryName);
    return repository;
}

@GetMapping("/repos/{userName}/{repositoryName}/{name}")
    public List<BranchDto> getUsersReposBranch(
            @PathVariable String userName,
            @PathVariable String repositoryName) {
    List<BranchDto> branches = githubService.fetchbranchesForReposiotry(userName, repositoryName);
    return branches;
}

    @GetMapping("/repos/{userName}/{repositoryName}/{name}/{branch}")
    public List<BranchDto> getCommitFromUserBranch(
            @PathVariable String userName,
            @PathVariable String repositoryName,
            @PathVariable String branch
    ) {
        List<BranchDto> commits = githubService.fetchbranchesForReposiotry(userName, repositoryName, branch);
        return commits;
    }
}
