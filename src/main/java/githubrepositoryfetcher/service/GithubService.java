package githubrepositoryfetcher.service;


import githubrepositoryfetcher.exception.RepositoryNotFoundException;
import githubrepositoryfetcher.exception.UserNotFoundException;
import githubrepositoryfetcher.httpClient.HttpConfiguration;
import githubrepositoryfetcher.model.BranchDto;
import githubrepositoryfetcher.model.InternalServerException;
import githubrepositoryfetcher.model.RepositoryDto;
import githubrepositoryfetcher.response.GitHubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final RestTemplate restTemplate;

    public List<RepositoryDto> fetchRepositories(String userName, String repositoryName) {
        String url = String.format(HttpConfiguration.GIT_HUB_BASE_URI_AND_USER_NAME, userName);
        try {
            GitHubRepositoryResponse[] response = restTemplate.getForObject(url, GitHubRepositoryResponse[].class);

            if (response == null || response.length == 0) {
                return List.of();
            }
            List<RepositoryDto> filteredRepos = Arrays.stream(response)
                    .filter(repository -> !repository.fork())
                    .filter(repository -> repository.name().equalsIgnoreCase(repositoryName))
                    .map(repository -> new RepositoryDto(
                            repository.name(),
                            repository.owner().login(),
                            repository.fork()
                    ))
                    .toList();
            if (filteredRepos.isEmpty()) {
                throw new RepositoryNotFoundException(repositoryName);
            }
            return filteredRepos;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(userName);
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public List<BranchDto> fetchbranchesForReposiotry(String userName, String reposiotryName) {
        List<RepositoryDto> filteredRepos = fetchRepositories(userName, reposiotryName);
        if (filteredRepos.isEmpty()) {
            throw new RepositoryNotFoundException(reposiotryName);
        }
        RepositoryDto repo = filteredRepos.get(0);
        return fetchbranches(repo.ownerLogin(), repo.name());
    }

    public List<BranchDto> fetchbranches(String userName, String repositoryName) {
        try {
            String url = String.format(HttpConfiguration.USER_REPO_NAME, userName, repositoryName);
            BranchDto[] response = restTemplate.getForObject(url, BranchDto[].class);
            if (response == null || response.length == 0) {
                return List.of();
            }
            return Arrays.asList(response);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RepositoryNotFoundException(repositoryName);
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }
}