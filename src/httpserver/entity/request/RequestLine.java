package httpserver.entity.request;

public record RequestLine(String method, String requestTarget, String protocol, String httpVersion) {
    @Override
    public String toString() {
        return method + " " + requestTarget + " " + protocol + "/" + httpVersion;
    }
}
