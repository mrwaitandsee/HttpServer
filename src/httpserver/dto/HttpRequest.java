package httpserver.dto;

import httpserver.entity.Headers;
import httpserver.entity.Parameter;
import httpserver.entity.request.RequestBody;
import httpserver.entity.request.RequestLine;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody body;
    private final List<Parameter> getParameters;
    private final List<Parameter> friendlyPathVariables;

    public HttpRequest(RequestLine requestLine, Headers headers, RequestBody body) {
        this.getParameters = new ArrayList<>();
        this.friendlyPathVariables = new ArrayList<>();
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public List<Parameter> getGetParameters() {
        return getParameters;
    }

    public List<Parameter> getFriendlyPathVariables() {
        return friendlyPathVariables;
    }

    public String getGetParameterByKey(String key) {
        for (var parameter: getParameters) {
            if (parameter.key().equals(key)) return parameter.value();
        }
        return null;
    }

    public String getFriendlyPathVariablesByKey(String key) {
        for (var parameter: friendlyPathVariables) {
            if (parameter.key().equals(key)) return parameter.value();
        }
        return null;
    }
}
