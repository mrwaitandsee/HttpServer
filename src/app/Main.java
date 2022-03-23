package app;

import httpserver.HttpServer;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        new HttpServer(8080, 3)
                .filter("GET", "/", (request, response) -> {
                    response.setBody("{\"key\":\"" + UUID.randomUUID() + "\"}");
                    response.setContentType("application/json");
                })
                .filter("GET", "/echo/:text", (request, response) -> {
                    response.setContentType("application/json");
                    var text = request.getFriendlyPathVariablesByKey("text");
                    response.setBody("{\"text\":\"" + text + "\"}");
                })
                .run();
    }
}
