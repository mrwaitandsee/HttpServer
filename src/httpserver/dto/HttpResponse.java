package httpserver.dto;

import httpserver.entity.Header;
import httpserver.entity.Headers;

import java.util.ArrayList;

public class HttpResponse {
    private String protocol = "HTTP";
    private String protocolVersion = "1.1";
    private int status = 200;
    private String statusMessage = "OK";
    private String contentType = "text/html";
    private Headers headers = new Headers(new ArrayList<>());
    private String body;

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setStatus(int status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addHeader(String key, String value) {
        headers.list().add(new Header(key, value));
    }

    @Override
    public String toString() {
        var result = "";
        result += protocol + "/" + protocolVersion + " " + status + " " + statusMessage + "\r\n";
        result += "Content-Type: " + contentType + "\r\n";
        result += headers.toString();
        result += "\r\n";
        result += body;
        result += "\r\n";
        return result;
    }

//    public void sendResponse() throws IOException {
//        System.out.println(headers.toString());
//        writer.write(protocol + "/" + protocolVersion + "\r\n");
//        writer.write("Content-Type: " + contentType + "\r\n");
//        writer.write(headers.toString());
//        writer.write("\r\n");
//        writer.write(body);
//        writer.write("\r\n");
//    }
}
