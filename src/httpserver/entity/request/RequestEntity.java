package httpserver.entity.request;

import httpserver.entity.Headers;

import java.util.ArrayList;

public class RequestEntity {
    private RequestLine requestLine;
    private Headers headers;
    private RequestBody body;

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        var list = new ArrayList<String>();
        if (!requestLine.toString().isEmpty()) list.add(requestLine.toString());
        if (!headers.toString().isEmpty()) list.add(headers.toString());
        if (!body.toString().isEmpty()) list.add(body.toString());
        return String.join("\n", list);
    }
}
