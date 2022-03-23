package httpserver.router;

import httpserver.comparator.RouteComparator;
import httpserver.dto.HttpRequest;
import httpserver.dto.HttpResponse;
import httpserver.entity.Parameter;
import httpserver.parser.RequestTargetParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router {
    private final RouteComparator routeComparator = new RouteComparator();
    private final List<Route> routes;

    public Router() {
        routes = new ArrayList<>();
    }

    public void addRoute(String method, String url, Callback callback) {
        routes.add(new Route(method, url, callback));
        sortRoutes();
    }

    private void sortRoutes() {
        var simpleRoutes = new ArrayList<Route>();
        var routesWithVariables = new ArrayList<Route>();
        routes.forEach(route -> {
            if (route.getUrl().contains(":")) {
                routesWithVariables.add(route);
            } else {
                simpleRoutes.add(route);
            }
        });
        simpleRoutes.sort(routeComparator);
        routesWithVariables.sort(routeComparator);
        simpleRoutes.addAll(routesWithVariables);
        routes.clear();
        routes.addAll(simpleRoutes);
    }

    public void execute(HttpRequest request, HttpResponse response) throws IOException {
        var method = request.getRequestLine().method();
        var requestTargetSplit = request.getRequestLine().requestTarget().split("/\\?", 2);
        var url = "";
        if (requestTargetSplit.length == 2) {
            url = requestTargetSplit[0];
            request.getGetParameters().addAll(RequestTargetParser.parseGetParameters(requestTargetSplit[1]));
        } else {
            if (requestTargetSplit[0].endsWith("/"))
                requestTargetSplit[0] = requestTargetSplit[0].substring(0, requestTargetSplit[0].length() - 1);
            url = requestTargetSplit[0];
        }

        var urlArray = url.split("/");

        for (var route: routes) {
            if (!route.getUrl().contains(":")) {
                if (route.getMethod().equals(method) && route.getUrl().equals(url)) {
                    route.execute(request, response);
                    return;
                }
            } else {
                var routeUrlArray = route.getUrl().split("/");
                if (urlArray.length != routeUrlArray.length) continue;
                for (var i = 0; i < urlArray.length; i++) {
                    if (routeUrlArray[i].contains(":")) {
                        var key = routeUrlArray[i].substring(1);
                        var value = urlArray[i];
                        request.getFriendlyPathVariables().add(new Parameter(key, value));
                    }
                }
                route.execute(request, response);
                return;
            }
        }
    }
}
