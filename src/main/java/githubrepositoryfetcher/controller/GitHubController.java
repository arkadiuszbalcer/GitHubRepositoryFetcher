package githubrepositoryfetcher.controller;

import githubrepositoryfetcher.model.RepositoryDto;
import githubrepositoryfetcher.service.GithubService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/repos/{userName}")
    public List<RepositoryDto> getAllUserRepositories(@PathVariable String userName) {
        return githubService.fetchAllRepositories(userName);
    }

}
