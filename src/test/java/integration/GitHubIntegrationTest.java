package integration;

import githubrepositoryfetcher.GitHubRepositoryFetcherApplication;
import githubrepositoryfetcher.model.BranchDto;
import githubrepositoryfetcher.model.RepositoryDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GitHubRepositoryFetcherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubIntegrationTest {
    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(GitHubIntegrationTest.class);


    @Test
    void should_Return_Non_Fork_Repositories_with_Branches(){
        // given
        String userName = "arkadiuszbalcer"; // zakładamy, że konto istnieje i ma przynajmniej jedno repo bez forka
        String baseUrl = "http://localhost:" + port;

        // when
        ResponseEntity<RepositoryDto[]> response = restTemplate.getForEntity(
                baseUrl + "/github/repos/" + userName, RepositoryDto[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP 200 OK response");
        RepositoryDto[] repositories = response.getBody();

        assertNotNull(repositories, "Response body is null");

        assertTrue(repositories.length > 0, "Expected at least one repository");
            logger.info("Owner login: {}", userName);

        for (RepositoryDto repo : repositories) {
            logger.info("Repository: {}", repo.name());
            assertNotNull(repo.name(), "Repository name should not be null");
            assertNotNull(repo.ownerLogin(), "Repository owner login should not be null");
            assertEquals(userName, repo.ownerLogin(), "Owner login should match the requested user");

            List<BranchDto> branches = repo.branches();
            assertNotNull(branches, "Branches list should not be null");
            assertTrue(branches.size() > 0, "Each repository should have at least one branch");

            for (BranchDto branch : branches) {
                logger.info(" └── Branch: {} (SHA: {})", branch.name(), branch.lastCommit());
                assertNotNull(branch.name(), "Branch name should not be null");
                assertNotNull(branch.lastCommit(), "Last commit SHA should not be null");
            }
        }
    }
}