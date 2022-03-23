package httpserver.router;

import httpserver.dto.HttpRequest;
import httpserver.dto.HttpResponse;

import java.io.IOException;

public interface Callback {
    void execute(HttpRequest request, HttpResponse httpResponse) throws IOException;
}
