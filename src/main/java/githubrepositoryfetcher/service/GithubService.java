package githubrepositoryfetcher.service;

import githubrepositoryfetcher.GitHubRepositoryFetcherApplication;
import githubrepositoryfetcher.exception.RepositoryNotFoundException;
import githubrepositoryfetcher.exception.UserNotFoundException;
import githubrepositoryfetcher.httpClient.HttpConfiguration;
import githubrepositoryfetcher.model.BranchDto;
import githubrepositoryfetcher.model.InternalServerException;
import githubrepositoryfetcher.model.RepositoryDto;
import githubrepositoryfetcher.response.GitHubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final RestTemplate restTemplate;

    public List<RepositoryDto> fetchRepositories(String userName, String repositoryName) {
        String url = String.format(HttpConfiguration.GIT_HAB_BASE_URI_AND_USER_NAME, userName);
        try {
            GitHubRepositoryResponse[] response = restTemplate.getForObject(url, GitHubRepositoryResponse[].class);

            if (response == null || response.length == 0) {
                return List.of();
            }
            List<RepositoryDto> filteredRepos = Arrays.stream(response)
                    .filter(repository -> !repository.fork())
                    .filter(repository-> repository.name().equalsIgnoreCase(repositoryName))
                    .map(repository -> new RepositoryDto(
                            repository.name(),
                            repository.owner().login(),
                            repository.fork()
                    ))
                    .toList();
            if(filteredRepos.isEmpty()){
                throw new RepositoryNotFoundException(repositoryName);
            }
            return filteredRepos;
        } catch (HttpClientErrorException.NotFound  e) {
            throw new UserNotFoundException(userName);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
    }