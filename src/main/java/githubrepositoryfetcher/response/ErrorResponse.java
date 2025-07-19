package githubrepositoryfetcher.response;

public record ErrorResponse(
        int status,
        String message
) {
}
