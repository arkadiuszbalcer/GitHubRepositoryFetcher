package githubrepositoryfetcher.service;


import githubrepositoryfetcher.exception.RepositoryNotFoundException;
import githubrepositoryfetcher.exception.UserNotFoundException;
import githubrepositoryfetcher.httpClient.HttpConfiguration;
import githubrepositoryfetcher.model.BranchDto;
import githubrepositoryfetcher.exception.InternalServerException;
import githubrepositoryfetcher.model.RepositoryDto;
import githubrepositoryfetcher.response.GitHubBranchResponse;
import githubrepositoryfetcher.response.GitHubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(GithubService.class);

    public List<RepositoryDto> fetchAllRepositories(String userName) {
        String url = String.format(HttpConfiguration.GIT_HUB_BASE_URI_AND_USER_NAME, userName);
        try {
            GitHubRepositoryResponse[] response = restTemplate.getForObject(url, GitHubRepositoryResponse[].class);

            if (response == null || response.length == 0) {
                return List.of();
            }

            return Arrays.stream(response)
                    .filter(repository -> !repository.fork())
                    .map(repository -> {
                        List<BranchDto> branches = fetchBranches(userName, repository.name());
                        return RepositoryDto.builder()
                                .name(repository.name())
                                .ownerLogin(repository.owner().login())
                                .branches(branches)
                                .build();
                    })
                    .toList();


        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(userName);
        } catch (Exception e) {
            logger.error("Unexpected error in fetch all repositories", e);
            throw new InternalServerException();
        }
    }


    public List<BranchDto> fetchBranches(String userName, String repositoryName) {
        try {
            String url = String.format(HttpConfiguration.USER_REPO_NAME, userName, repositoryName);
            GitHubBranchResponse[] response = restTemplate.getForObject(url, GitHubBranchResponse[].class);
            if (response == null || response.length == 0) {
                return List.of();
            }
            return Arrays.stream(response)
                    .map(branchDto -> BranchDto.builder()
                            .name(branchDto.name())
                            .lastCommit(branchDto.commit().sha())
                            .build())
                    .toList();

        } catch (HttpClientErrorException.NotFound ex) {
            logger.warn("User not found: {}", userName);
            throw new RepositoryNotFoundException(repositoryName);
        } catch (Exception e) {
            logger.warn("Internal server error while fetching repositories", e);
            throw new InternalServerException();
        }
    }
}