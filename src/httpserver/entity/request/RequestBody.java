package httpserver.entity.request;

public record RequestBody(String body) {
    @Override
    public String toString() {
        return body;
    }
}
