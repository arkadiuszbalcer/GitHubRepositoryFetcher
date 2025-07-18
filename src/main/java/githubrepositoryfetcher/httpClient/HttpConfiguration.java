package githubrepositoryfetcher.httpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static final String GIT_HAB_BASE_URI = "https://developer.github.com/v3";
    public static final String USER_NAME = GIT_HAB_BASE_URI +"/users/%s/repos";
    private static final String REPO_NAME = GIT_HAB_BASE_URI + "/repos/%s/%s/branches";
}
