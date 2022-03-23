package httpserver.core;

import httpserver.dto.HttpResponse;
import httpserver.parser.HttpRequestParser;
import httpserver.router.Router;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;

import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private final Socket socket;
    private final Router router;

    public RequestHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    public String read(InputStream inputStream) throws Exception {
        StringBuilder result = new StringBuilder();
        do {
            result.append((char) inputStream.read());
        } while (inputStream.available() > 0);
        return result.toString();
    }

    @Override
    public void run() {
        try {
            var req = read(socket.getInputStream());

            try {
                var request = new HttpRequestParser(req.getBytes()).parse();

                var out = new BufferedWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        socket.getOutputStream())));

                var response = new HttpResponse();
                router.execute(request, response);

                out.write(response.toString());

                out.flush();
                out.close();
                socket.close();
            } catch (Exception exception) {
                // ignored
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
