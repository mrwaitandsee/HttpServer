package httpserver.router;

import httpserver.dto.HttpRequest;
import httpserver.dto.HttpResponse;

import java.io.IOException;

public class Route {
    private final String method;
    private final String url;
    private final Callback callback;

    public Route(String method, String url, Callback callback) {
        this.method = method;
        this.url = url;
        this.callback = callback;
    }

    public String getMethod() { return method; }

    public String getUrl() { return url; }

    public void execute(HttpRequest request, HttpResponse response) throws IOException {
        callback.execute(request, response);
    }

    @Override
    public String toString() {
        return method + " " + url;
    }
}
