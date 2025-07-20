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
    public static final String GIT_HUB_BASE_URI = "https://api.github.com";
    public static final String GIT_HUB_BASE_URI_AND_USER_NAME = GIT_HUB_BASE_URI +"/users/%s/repos";
    public static final String USER_REPO_NAME = GIT_HUB_BASE_URI + "/repos/%s/%s/branches";
}
