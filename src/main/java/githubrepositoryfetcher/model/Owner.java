package githubrepositoryfetcher.model;

import lombok.Getter;

@Getter
public record Owner(
        String login
) {
}
