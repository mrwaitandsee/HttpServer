package httpserver.parser;

import httpserver.dto.HttpRequest;
import httpserver.entity.Header;
import httpserver.entity.Headers;
import httpserver.entity.request.RequestBody;
import httpserver.entity.request.RequestEntity;
import httpserver.entity.request.RequestLine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpRequestParser {
    private static final String SPACE_SEPARATOR = " ";
    private static final String SLASH_SEPARATOR = "/";
    private static final String HEADER_SEPARATOR = ": ";

    private final byte[] bytes;

    public HttpRequestParser(byte[] bytes) {
        this.bytes = bytes;
    }

    public HttpRequest parse() throws Exception {
        return parseFromBytes(bytes);
    }

    private HttpRequest parseFromBytes(byte[] bytes) {
        var lines = new String(bytes, StandardCharsets.UTF_8).split(System.lineSeparator());

        var body = new StringBuilder();

        var headers = new Headers(new ArrayList<>());

        var requestEntity = new RequestEntity();

        requestEntity.setHeaders(headers);

        var step = ParsingStep.requestLine;

        for (String line : lines) {
            if (step.equals(ParsingStep.requestLine)) {
                var requestLine = parseRequestLine(line);
                requestEntity.setRequestLine(requestLine);
                step = ParsingStep.headers;
                continue;
            }
            if (step.equals(ParsingStep.headers)) {
                if (line.equals("")) {
                    step = ParsingStep.body;
                } else {
                    headers.list().add(parseHeader(line));
                }
                continue;
            }
            body.append(line);
        }

        requestEntity.setBody(new RequestBody(body.toString()));

        return new HttpRequest(
                requestEntity.getRequestLine(),
                requestEntity.getHeaders(),
                requestEntity.getBody()
        );
    }

    private RequestLine parseRequestLine(String line) {
        var lines = line.split(SPACE_SEPARATOR);
        var method = lines[0];
        var requestTarget = lines[1];
        lines = lines[2].split(SLASH_SEPARATOR);
        var protocol = lines[0];
        var httpVersion = lines[1];
        return new RequestLine(method, requestTarget, protocol, httpVersion);
    }

    private Header parseHeader(String line) {
        var lines = line.split(HEADER_SEPARATOR, 2);
        return new Header(lines[0], lines[1]);
    }
}
