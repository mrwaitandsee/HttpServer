package httpserver;

import httpserver.core.RequestHandler;
import httpserver.router.Callback;
import httpserver.router.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private int nThreads;

    private ServerSocket serverSocket;
    private ExecutorService executorService;

    private final Router router;

    public HttpServer() {
        this.port = 8080;
        this.nThreads = 1;
        router = new Router();
    }

    public HttpServer(int port, int nThreads) {
        setPort(port);
        setThreads(nThreads);
        router = new Router();
    }

    public HttpServer filter(String method, String url, Callback callback) {
        router.addRoute(method, url.equals("/") ? "" : url, callback);
        return this;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            executorService = Executors.newFixedThreadPool(nThreads);
            while (true) {
                var handler = new RequestHandler(serverSocket.accept(), router);
                executorService.submit(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            executorService.shutdownNow();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreads() {
        return nThreads;
    }

    public void setThreads(int nThreads) {
        this.nThreads = nThreads;
    }
}
